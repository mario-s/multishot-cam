package de.mario.camera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Main activity.
 * 
 * @author Mario
 * 
 */
public class PhotoActivity extends Activity {

	private static final String NO_CAM = "No camera on this device";
	private static final String NO_BACK_CAM = "No back facing camera found.";
	private static final int NO_CAM_ID = -1;
	final static String DEBUG_TAG = "PhotoActivity";
	private Camera camera;
	private Preview preview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, NO_CAM, Toast.LENGTH_LONG).show();
		} else {
			int id = findBackCamera();
			if (id == NO_CAM_ID) {
				Toast.makeText(this, NO_BACK_CAM, Toast.LENGTH_LONG).show();
			} else {
				camera = Camera.open(id);
				preview = new Preview(this, camera);
				FrameLayout layout = (FrameLayout) findViewById(R.id.preview);
				layout.addView(preview);

			}
		}
	}

	private int findBackCamera() {
		int cameraId = NO_CAM_ID;
		// Search for the back facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
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
	protected void onPause() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

	public void onClick(View view) {
		
		PhotoHandler photoHandler = new PhotoHandler(getApplicationContext());
		
		Camera.Parameters params = camera.getParameters();
		
		photoHandler.setDefaultExposureCompensation(params.getExposureCompensation());
		
		params.setExposureCompensation(params.getMinExposureCompensation());
		camera.setParameters(params);
		
		camera.takePicture(null, null, photoHandler);
	}
}
