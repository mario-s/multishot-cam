package de.mario.camera;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.mario.camera.service.ExposureMergeService;
import de.mario.camera.service.OpenCvService;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static java.lang.Integer.parseInt;

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

	public PhotoActivity() {
		exposureValues = new LinkedList<>();
		handler = new MessageHandler(this);
		receiver = new ProcessReceiver();
		executor = new ScheduledThreadPoolExecutor(1);
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
			CameraLookup lookup = new CameraLookup();
			camId = lookup.findBackCamera();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (camId == CameraLookup.NO_CAM_ID) {
			toast(getResource(R.string.no_back_cam));
		} else {
			camera = Camera.open(camId);
			preview = new Preview(this, camera);
			getPreviewLayout().addView(preview);

			fillExposuresValues();
		}

		registerReceiver(receiver, new IntentFilter(EXPOSURE_MERGE));
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
		Camera.Parameters params = camera.getParameters();
		exposureValues.clear();
		exposureValues.add(params.getExposureCompensation());
		exposureValues.add(params.getMinExposureCompensation());
		exposureValues.add(params.getMaxExposureCompensation());
	}

	@Override
	protected void onPause() {
		getPreviewLayout().removeView(preview);
		preview = null;
		releaseCamera();
		unregisterReceiver(receiver);
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
		PhotoCommand command = new PhotoCommand(this, camera);
		int delay = getDelay();
		if(delay > MIN){
			executor.schedule(command, delay, TimeUnit.SECONDS);
		}else {
			executor.execute(command);
		}
	}

	/**
	 * Action handler for settings button.
	 * @param view the {@link View} for this action.
	 */
	public void onSettings(View view) {
		startActivity(new Intent(this, SettingsActivity.class));
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

	private int getDelay() {
		return parseInt(getPreferences().getString("shutterDelayTime", "0"));
	}

	private boolean isProcessingEnabled() {
		return getPreferences().getBoolean("processHdr", false);
	}

	private SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this);
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
		return getExternalStoragePublicDirectory(DIRECTORY_DCIM);
	}

	@Override
	public File getInternalDirectory() {
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
		return cw.getDir("data", Context.MODE_PRIVATE);
	}

	void setExecutor(ScheduledExecutorService executor) {
		this.executor = executor;
	}

	private void processHdr(String [] pictures){
		if(isProcessingEnabled()) {
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

	static class MessageHandler extends Handler {

		private final PhotoActivity activity;

		MessageHandler(PhotoActivity activity) {
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
				activity.processHdr(pictures);

				informAboutPictures(pictures);
			}
		}

		private void informAboutPictures(String[] pictures) {
			int len = pictures.length;
			File dir = activity.getPicturesDirectory();
			activity.toast(String.format(activity.getString(R.string.photos_saved), len, dir));
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

	private class OpenCvLoaderCallback extends BaseLoaderCallback {
		private final Intent intent;

		OpenCvLoaderCallback(Context context, Intent intent){
			super(context);
			this.intent = intent;
		}

		@Override
		public void onManagerConnected(int status) {
			if (status  == LoaderCallbackInterface.SUCCESS) {
				startService(intent);
			}else{
				super.onManagerConnected(status);
			}
		}
	}

}
