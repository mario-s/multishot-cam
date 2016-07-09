package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.inject.Inject;

import org.opencv.android.OpenCVLoader;

import java.io.File;

import de.mario.photo.controller.CameraControlable;
import de.mario.photo.controller.preview.CanvasView;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Main activity.
 * 
 * @author Mario
 * 
 */
@ContentView(R.layout.activity_photo)
public class PhotoActivity extends RoboActivity implements PhotoActivable{

	private static final String VERS = "version";
	private static final String PREFS = "PREFERENCE";
	@InjectView(R.id.progress_bar)
	private View progressBar;
	@Inject
	private CanvasView canvasView;
	@Inject
	private MyLocationListener locationListener;
	@Inject
	private LocationManager locationManager;
	@Inject
	private SettingsAccess settingsAccess;
	@Inject
	private CameraControlable cameraController;
	@Inject
	private IntentFactory intentFactory;

	private MessageHandler handler;
	private ProcessedMessageReceiver receiver;

	private ViewsOrientationListener orientationListener;

	private boolean hasCam;

	public PhotoActivity() {
		handler = new MessageHandler(this);
		receiver = new ProcessedMessageReceiver(this);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			toast(getString(R.string.no_cam));
		} else {
			showDialogWhenFirstRun();

			cameraController.setActivity(this);
			hasCam = cameraController.lookupCamera();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!hasCam) {
			toast(getString(R.string.no_back_cam));
		} else {
			initialize();
		}

		registerReceiver(receiver, new IntentFilter(EXPOSURE_MERGE));
	}

	private void initialize() {
		cameraController.initialize();

		getPreviewLayout().addView(cameraController.getPreview(), 0);
		getPreviewLayout().addView(canvasView, 1);
		getPreviewLayout().addView(cameraController.getFocusView(), 2);
	}

	private void registerLocationListener() {
		if(isGeoTaggingEnabled()) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
	}

	private void registerViewsOrientationListener(){
		if(orientationListener == null){
			orientationListener = new ViewsOrientationListener(this);
			orientationListener.addView(findViewById(R.id.shutter));
			orientationListener.addView(findViewById(R.id.settings));
			orientationListener.enable();
		}
	}

	private void unregisterLocationListener() {
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();

		registerLocationListener();
		registerViewsOrientationListener();

		cameraController.reinitialize();
	}

	private void showDialogWhenFirstRun() {
		String current = getVersion();
		String stored = getSharedPreferences(PREFS, MODE_PRIVATE).getString(VERS, "");
		if (!stored.equals(current)){
			new StartupDialog(this).show();
			getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString(VERS, current).apply();
		}
	}

	private String getVersion() {
		return BuildConfig.VERSION_NAME + BuildConfig.VERSION_CODE;
	}

	private ViewGroup getPreviewLayout() {
		return (ViewGroup) findViewById(R.id.preview);
	}

	void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	protected void onPause() {
		getPreviewLayout().removeAllViews();
		cameraController.releaseCamera();
		unregisterReceiver(receiver);
		unregisterLocationListener();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

	/**
	 * Action handler for shutter button.
	 * @param view the {@link View} for this action.
	 */
	public void onShutter(View view) {
		toggleInputs(false);
		cameraController.shot();
	}

	@Override
	public void prepareForNextShot() {
		toggleInputs(true);
		cameraController.getFocusView().resetFocus();
	}

	/**
	 * Enables / disables all input elements, seen on the preview.
	 * @param enabled <code>true</code>: enables the elements.
	 */
	private void toggleInputs(boolean enabled) {
		findViewById(R.id.shutter).setEnabled(enabled);
		findViewById(R.id.settings).setEnabled(enabled);
	}

	/**
	 * Action handler for settings button.
	 * @param view the {@link View} for this action.
	 */
	public void onSettings(View view) {
		Intent intent = intentFactory.newSettingsIntent();

		hideProgress();
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_settings){
			onSettings(null);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//combined settings values
	private boolean isGeoTaggingEnabled() { return isGpsEnabled() && settingsAccess.isGeoTaggingEnabled();}

	private boolean isGpsEnabled() { return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}
	//end settings values

	Location getCurrentLocation() {
		return locationListener.getCurrentLocation();
	}

	@Override
	public Handler getHandler() {
		return handler;
	}

	@Override
	public SettingsAccess getSettingsAccess() {
		return settingsAccess;
	}

	void processHdr(String [] pictures){
		if(settingsAccess.isProcessingEnabled()) {
			showProgress();
			OpenCvLoaderCallback callback = new OpenCvLoaderCallback(this, intentFactory.newOpenCvIntent(pictures));
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, callback);
		}
	}

	private void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	void hideProgress() {
		progressBar.setVisibility(View.GONE);
	}

	void refreshPictureFolder(String path){
		File file = new File(path);
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
	}
}
