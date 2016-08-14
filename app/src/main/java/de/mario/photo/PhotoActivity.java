package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.inject.Inject;

import org.opencv.android.OpenCVLoader;

import java.io.File;

import de.mario.photo.controller.CameraControlable;
import de.mario.photo.service.ExposureMergeService;
import de.mario.photo.service.OpenCvService;
import de.mario.photo.settings.SettingsAccess;
import de.mario.photo.settings.SettingsIntentFactory;
import de.mario.photo.support.BitmapLoader;
import de.mario.photo.support.GalleryOpener;
import de.mario.photo.support.ImageOpener;
import de.mario.photo.support.MediaUpdater;
import de.mario.photo.view.GridView;
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

	private static final int IMG_BTN_THUMB = 38;

	private static final int[] VIEW_IDS = new int[]{R.id.shutter_button, R.id.settings_button,
			R.id.gallery_button, R.id.image_button};

	@InjectView(R.id.progress_bar)
	private View progressBar;
	@InjectView(R.id.image_button)
	private ImageView imageButton;
	@Inject
	private GridView canvasView;
	@Inject
	private MyLocationListener locationListener;
	@Inject
	private LocationManager locationManager;
	@Inject
	private SettingsAccess settingsAccess;
	@Inject
	private CameraControlable cameraController;
	@Inject
	private SettingsIntentFactory intentFactory;
	@Inject
	private MediaUpdater mediaUpdater;
	@Inject
	private ImageOpener imageOpener;
	@Inject
	private GalleryOpener galleryOpener;
	@Inject
	private BitmapLoader bitmapLoader;
	@Inject
	private StartupDialog startupDialog;

	private MessageHandler handler;
	private ProcessedMessageReceiver receiver;

	private ViewsOrientationListener orientationListener;

	private ImageToast imageToast;

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
		createImageToast();

		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			toast(getString(R.string.no_cam));
		} else {
			onPostCreate();
		}
	}

	private void onPostCreate() {
		bitmapLoader.setThumbnailSize(IMG_BTN_THUMB);
		startupDialog.showIfFirstRun();
	}

	private void createImageToast() {
		View view = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast));
		imageToast = new ImageToast(view);
	}

	@Override
	protected void onStart() {
		super.onStart();
		cameraController.setActivity(this);
		boolean hasCam = cameraController.lookupCamera();
		if (!hasCam) {
			toast(getString(R.string.no_back_cam));
		} else {
			onPostStart();
		}
	}

	private void onPostStart() {
		registerReceiver(receiver, new IntentFilter(EXPOSURE_MERGE));

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
		if (orientationListener == null) {
			orientationListener = new ViewsOrientationListener(this);
			for (int id : VIEW_IDS) {
				orientationListener.addView(findViewById(id));
			}
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

	private ViewGroup getPreviewLayout() {
		return (ViewGroup) findViewById(R.id.preview);
	}

	private void toast(int id) {
		toast(getString(id));
	}

	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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

	void prepareForNextShot() {
		toggleInputs(true);
	}

	/**
	 * Enables / disables all input elements, seen on the preview.
	 * @param enabled <code>true</code>: enables the elements.
	 */
	private void toggleInputs(boolean enabled) {
		for (int id : VIEW_IDS) {
			findViewById(id).setEnabled(enabled);
		}
		//will be enabled after image ist processed
		if (!enabled) {
			imageButton.setVisibility(View.GONE);
		}
	}

	/**
	 * Action handler for settings button.
	 * @param view the {@link View} for this action.
	 */
	public void onSettings(View view) {
		Intent intent = intentFactory.create();

		hideProgress();
		startActivity(intent);
	}

	public void onGallery(View view) {
		galleryOpener.open();
	}

	public void onImage(View view) {
		File last = mediaUpdater.getLastUpdated();

		if (last != null) {
			imageOpener.open(last);
		}
	}

	/**
	 * Activates or deactivates the button to view the merged image depending of the last result.
	 * It also hides the progress bar.
	 */
	void toggleImageButton() {
		hideProgress();
		File last = mediaUpdater.getLastUpdated();
		if (last != null) {
			Bitmap bitmap = bitmapLoader.loadThumbnail(last);
			imageButton.setImageBitmap(bitmap);
			imageButton.setVisibility(View.VISIBLE);
		} else {
			imageButton.setVisibility(View.GONE);
		}
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
	private boolean isGeoTaggingEnabled() { return isGpsEnabled() && settingsAccess.isEnabled(R.string.geotagging);}

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
		if(settingsAccess.isEnabled(R.string.processHdr)) {
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

	void refreshPictureFolder(String path){
		File file = new File(path);
		imageToast.setImage(file).show();
		mediaUpdater.sendUpdate(file);
	}
}
