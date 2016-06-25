package de.mario.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

import de.mario.camera.controller.CameraControlable;
import de.mario.camera.controller.CameraController;
import de.mario.camera.controller.lookup.StorageLookup;
import de.mario.camera.controller.preview.CanvasView;
import de.mario.camera.service.ExposureMergeService;
import de.mario.camera.service.OpenCvService;
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
	private CanvasView canvasView;

	private MyLocationListener locationListener;
	@Inject
	private LocationManager locationManager;

	private Handler handler;
	private ProcessReceiver receiver;

	private SettingsAccess settingsAccess;
	private CameraControlable cameraController;
	private ViewsOrientationListener orientationListener;

	private File pictureDirectory;

	private boolean hasCam;

	public PhotoActivity() {
		handler = new MessageHandler(this);
		receiver = new ProcessReceiver();
		locationListener = new MyLocationListener();
		settingsAccess = new SettingsAccess(this);
		cameraController = new CameraController(this);
	}

	void setCameraController(CameraController cameraController){
		this.cameraController = cameraController;
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
			toast(getResource(R.string.no_cam));
		} else {
			showDialogWhenFirstRun();

			hasCam = cameraController.lookupCamera();

			StorageLookup storageLookup = new StorageLookup(this);
			pictureDirectory = storageLookup.createStorageDirectory();

		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!hasCam) {
			toast(getResource(R.string.no_back_cam));
		} else {
			initialize();
		}

		registerReceiver(receiver, new IntentFilter(EXPOSURE_MERGE));
	}

	private void initialize() {
		cameraController.initialize();

		canvasView = new CanvasView(this);
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

	@Override
	public String getResource(int key) {
		return getString(key);
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
		Intent intent = newIntent();

		hideProgress();
		startActivity(intent);
	}

	private Intent newIntent() {
		Intent intent = new Intent(this, SettingsActivity.class);

		intent.putExtra("pictureSizes", cameraController.getSupportedPicturesSizes());
		intent.putExtra("selectedPictureSize", cameraController.getSelectedPictureSize());

		String isoKey = findIsoKey();
		if(!isoKey.isEmpty()) {
			intent.putExtra("selectedIso", cameraController.getSelectedIsoValue(isoKey));
			intent.putExtra("isos", cameraController.getIsoValues());
		}
		return intent;
	}

	private String findIsoKey() {
		//exists the ISO key in the settings?
		String isoKey = settingsAccess.getIsoKey();
		if(isoKey.isEmpty()) {
			//if not look for it
			isoKey = cameraController.findIsoKey();
			settingsAccess.setIsoKey(isoKey);
		}
		return isoKey;
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
	public File getPicturesDirectory() {
		return pictureDirectory;
	}

	@Override
	public SettingsAccess getSettingsAccess() {
		return settingsAccess;
	}

	void processHdr(String [] pictures){
		if(settingsAccess.isProcessingEnabled()) {
			showProgress();
			Intent intent = new Intent(this, ExposureMergeService.class);
			intent.putExtra(OpenCvService.PARAM_PICS, pictures);
			OpenCvLoaderCallback callback = new OpenCvLoaderCallback(this, intent);
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, callback);
		}
	}

	private void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	private void hideProgress() {
		progressBar.setVisibility(View.GONE);
	}

	private class ProcessReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(EXPOSURE_MERGE)){
				hideProgress();
				String result = intent.getStringExtra("merged");
				toast(result);
			}
		}
	}
}
