package de.mario.camera;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

	private static final String NO_CAM = "No camera on this device";
	private static final String NO_BACK_CAM = "No back facing camera found.";
	private static final int NO_CAM_ID = -1;
	public static final int MIN = 0;
	private Camera camera;
	private Preview preview;
	private final LinkedList<Integer> exposureValues;
	private Handler handler;
	private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
	private int camId = NO_CAM_ID;

	public PhotoActivity() {
		exposureValues = new LinkedList<>();
		handler = new MessageHandler(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			toast(NO_CAM);
		} else {
			camId = findBackCamera();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (camId == NO_CAM_ID) {
			toast(NO_BACK_CAM);
		} else {
			camera = Camera.open(camId);
			preview = new Preview(this, camera);
			getFrameLayout().addView(preview);

			fillExposuresValues();
		}
	}

	private FrameLayout getFrameLayout() {
		return (FrameLayout) findViewById(R.id.preview);
	}

	private int findBackCamera() {
		int cameraId = NO_CAM_ID;
		// Search for the back facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = MIN; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	public String getResource(int key) {
		return getApplicationContext().getResources().getString(key);
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
		getFrameLayout().removeView(preview);
		preview = null;
		releaseCamera();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_settings){
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view) {
		PhotoCommand command = new PhotoCommand(this, camera);
		int delay = getDelay();
		if(delay > MIN){
			executor.schedule(command, delay, TimeUnit.SECONDS);
		}else {
			executor.execute(command);
		}
	}

	private int getDelay() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		return parseInt(prefs.getString("shutterDelayTime", "0"));
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
	public File getPicturesDirectory() {
		return getExternalStoragePublicDirectory(DIRECTORY_DCIM);
	}

	@Override
	public File getInternalDirectory() {
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
		return cw.getDir("data", Context.MODE_PRIVATE);
	}

	private void processHdr(String [] pictures){
		ProcessHdrService.startProcessing(getApplicationContext(), pictures);
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

				int len = pictures.length;
				File dir = activity.getPicturesDirectory();
				activity.toast(String.format(activity.getResource(R.string.photos_saved), len, dir));
			}
		}
	}

	class PhotoCommand implements Runnable {
		private final PhotoActivity activity;
		private final Camera camera;

		PhotoCommand(PhotoActivity activity, Camera camera){
			this.activity = activity;
			this.camera = camera;
		}

		@Override
		public void run() {
			ContinuesCallback callback = new ContinuesCallback(activity);
			camera.takePicture(null, null, callback);
		}
	}

}
