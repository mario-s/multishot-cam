package de.mario.camera;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.mario.camera.callback.PhotoCommand;
import de.mario.camera.exif.ExifTag;
import de.mario.camera.exif.ExifWriter;
import de.mario.camera.exif.GeoTagFactory;
import de.mario.camera.lookup.CameraLookup;
import de.mario.camera.lookup.StorageLookup;
import de.mario.camera.preview.CanvasView;
import de.mario.camera.preview.FocusView;
import de.mario.camera.preview.Preview;
import de.mario.camera.service.ExposureMergeService;
import de.mario.camera.service.OpenCvService;
import de.mario.camera.support.IsoSupport;
import de.mario.camera.support.PicturesSizeSupport;

/**
 * Main activity.
 * 
 * @author Mario
 * 
 */
public class PhotoActivity extends Activity implements PhotoActivable{

	private static final int MIN = 0;
	private static final String VERS = "version";
	private static final String PREFS = "PREFERENCE";
	private Camera camera;
	private Preview preview;
	private ProgressBar progressBar;
	private final LinkedList<Integer> exposureValues;
	private Handler handler;
	private ProcessReceiver receiver;
	private ScheduledExecutorService executor;
	private int camId = CameraLookup.NO_CAM_ID;
	private FocusView focusView;
	private CanvasView canvasView;
	private boolean canDisableShutterSound;
	private MyLocationListener locationListener;
	private LocationManager locationManager;
	private OrientationListener orientationListener;
	private File pictureDirectory;
	private SettingsAccess settingsAccess;
	private IsoSupport isoSupport;
	private PicturesSizeSupport sizeSupport;

	public PhotoActivity() {
		exposureValues = new LinkedList<>();
		handler = new MessageHandler(this);
		receiver = new ProcessReceiver();
		executor = new ScheduledThreadPoolExecutor(1);
		locationListener = new MyLocationListener();
		settingsAccess = new SettingsAccess(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);

		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			toast(getResource(R.string.no_cam));
		} else {
			showDialogWhenFirstRun();

			CameraLookup cameraLookup = new CameraLookup();
			camId = cameraLookup.findBackCamera();
			canDisableShutterSound = cameraLookup.canDisableShutterSound(camId);

			StorageLookup storageLookup = new StorageLookup(this);
			pictureDirectory = storageLookup.createStorageDirectory();

			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			orientationListener = new OrientationListener(this);
		}
	}


	@Override
	protected void onStart() {
		super.onStart();
		if (camId == CameraLookup.NO_CAM_ID) {
			toast(getResource(R.string.no_back_cam));
		} else {
			initCamera();

			fillExposuresValues();
		}

		registerReceiver(receiver, new IntentFilter(EXPOSURE_MERGE));
	}

	private void initCamera() {
		CameraFactory factory = new CameraFactory();
		camera = factory.getCamera(camId);
		sizeSupport = new PicturesSizeSupport(camera);
		isoSupport = new IsoSupport(camera);

		if(orientationListener.canDetectOrientation()){
			orientationListener.setCamera(camera);
			orientationListener.enable();
		}

		preview = new Preview(this, camera);
		canvasView = new CanvasView(this);
		focusView = new FocusView(this);
		getPreviewLayout().addView(preview, 0);
		getPreviewLayout().addView(canvasView, 1);
		getPreviewLayout().addView(focusView, 2);
	}

	private void registerLocationListener() {
		if(isGeoTaggingEnabled()) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
	}

	private void unregisterLocationListener() {
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();

		registerLocationListener();

		if(camera == null) {
			initCamera();
		}
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

	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG)
				.show();
	}

	private void fillExposuresValues() {
		ExposureValuesFactory factory = new ExposureValuesFactory(camera);
		exposureValues.clear();
		exposureValues.addAll(factory.getMinMaxValues());
	}

	@Override
	protected void onPause() {
		getPreviewLayout().removeAllViews();
		preview = null;
		releaseCamera();
		unregisterReceiver(receiver);
		unregisterLocationListener();
		orientationListener.disable();
		super.onPause();
	}

	private void releaseCamera() {
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
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
		if(isShutterSoundDisabled()){
			camera.enableShutterSound(false);
		}else{
			camera.enableShutterSound(true);
		}

		toggleInputs(false);
		camera.autoFocus(new Camera.AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				focusView.focused(success);
				if (success) {
					Runnable command = new PhotoCommand(PhotoActivity.this, camera);
					int delay = settingsAccess.getDelay();

					Log.d(DEBUG_TAG, "delay for photo: " + delay);

					if (delay > MIN) {
						executor.schedule(command, delay, TimeUnit.SECONDS);
					} else {
						executor.execute(command);
					}
				} else {
					prepareForNextShot();
				}
			}
		});
	}

	private void prepareForNextShot() {
		toggleInputs(true);
		focusView.resetFocus();
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
		Intent intent = new Intent(this, SettingsActivity.class);

		intent.putExtra("pictureSizes", sizeSupport.getSupportedPicturesSizes());
		intent.putExtra("selectedPictureSize", sizeSupport.getSelectedPictureSize(camera));

		String isoKey = findIsoKey();
		if(!isoKey.isEmpty()) {
			intent.putExtra("selectedIso", isoSupport.getSelectedIsoValue(isoKey));
			intent.putExtra("isos", isoSupport.getIsoValues());
		}

		startActivity(intent);
	}

	private String findIsoKey() {
		//exists the ISO key in the settings?
		String isoKey = settingsAccess.getIsoKey();
		if(isoKey.isEmpty()) {
			//if not look for it
			isoKey = isoSupport.findIsoKey();
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
	private boolean isShutterSoundDisabled() { return canDisableShutterSound && settingsAccess.isShutterSoundDisabled();}

	private boolean isGeoTaggingEnabled() { return isGpsEnabled() && settingsAccess.isGeoTaggingEnabled();}

	private boolean isGpsEnabled() { return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}
	//end settings values

	private Location getCurrentLocation() {
		return locationListener.getCurrentLocation();
	}

	@Override
	public LinkedList<Integer> getExposureValues() {
		return new LinkedList<>(exposureValues);
	}

	@Override
	public Handler getHandler() {
		return handler;
	}

	@Override
	public Preview getPreview() {
		return preview;
	}

	@Override
	public File getPicturesDirectory() {
		return pictureDirectory;
	}

	@Override
	public SettingsAccess getSettingsAccess() {
		return settingsAccess;
	}

	void setCamera(Camera camera) {
		this.camera = camera;
	}

	private void processHdr(String [] pictures){
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

	class MessageHandler extends Handler {

		private final PhotoActivity activity;

		MessageHandler(PhotoActivity activity) {
			super(Looper.getMainLooper());
			this.activity = activity;
		}

		@Override
		public void handleMessage(Message message) {
			Bundle bundle = message.getData();
			if(bundle.isEmpty()) {
				String msg = message.obj.toString();
				activity.toast(msg);
			}else{
				String[] pictures = bundle.getStringArray(
						PICTURES);
				updateExif(pictures);
				activity.processHdr(pictures);

				prepareForNextShot();
				informAboutPictures(pictures);
				Log.d(DEBUG_TAG, "ready for next photo session");
			}
		}

		private void informAboutPictures(String[] pictures) {
			int len = pictures.length;
			File dir = activity.getPicturesDirectory();
			activity.toast(String.format(activity.getString(R.string.photos_saved), len, dir));
		}

		private void updateExif(String [] pictures){
			Location location = activity.getCurrentLocation();
			Log.d(PhotoActivable.DEBUG_TAG, "location: " + location);
			if(location != null) {
				GeoTagFactory tagFactory = new GeoTagFactory();
				Map<ExifTag, String> tags = tagFactory.create(location);
				ExifWriter writer = new ExifWriter();
				for (String name : pictures) {
					File file = new File(name);
					writer.addTags(file, tags);
				}
			}
		}
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
