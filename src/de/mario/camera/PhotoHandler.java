package de.mario.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.widget.Toast;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.os.Environment.DIRECTORY_DCIM;

/**
 * Handler to save the image in a file.
 * 
 * @author Mario
 * 
 */
class PhotoHandler implements PictureCallback {

	private static final String JPG = ".jpg";
	private static final int REQ_IMAGES = 3;
	private static final String NO_DIR = "No directory to save image.";

	private final Context context;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmm");
	private File pictureFileDir;
	private int imageCounter;
	private int defaultExposureCompensation;
	private List<String> imagesNames = new ArrayList<String>(REQ_IMAGES);

	public PhotoHandler(Context context) {
		this(0, context);
	}

	public PhotoHandler(int imageCounter, Context context) {
		this.imageCounter = imageCounter;
		this.context = context;
		pictureFileDir = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		if (!pictureFileDir.exists()) {

			Log.d(PhotoActivity.DEBUG_TAG, NO_DIR);
			Toast.makeText(context, NO_DIR, Toast.LENGTH_LONG).show();
			return;
		}

		String pictureFilename = createFileName();
		try {
			File pictureFile = new File(pictureFileDir, pictureFilename);
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			imagesNames.add(pictureFile.getPath());
		} catch (Exception error) {
			Log.e(PhotoActivity.DEBUG_TAG, "File" + pictureFilename
					+ "not saved: " + error.getMessage());
			Toast.makeText(context, "Image could not be saved.",
					Toast.LENGTH_LONG).show();
		}

		imageCounter++;

		nextPhoto(camera);
	}

	private String createFileName() {
		String date = dateFormat.format(new Date());

		StringBuilder builder = new StringBuilder(25);
		builder.append("Picture_").append(date).append("_")
				.append(imageCounter)
				.append(JPG);

		return builder.toString();
	}

	private void nextPhoto(Camera camera) {
		Camera.Parameters params = camera.getParameters();
		// restart preview for next photo
		camera.startPreview();

		boolean next = false;

		if (imageCounter == 1) {
			next = true;
			params.setExposureCompensation(0);
		} else if (imageCounter == 2) {
			next = true;
			params.setExposureCompensation(params.getMaxExposureCompensation());
		} else {
			params.setExposureCompensation(defaultExposureCompensation);
			if (imagesNames.size() == REQ_IMAGES) {
				Toast.makeText(context, "New Images saved.", Toast.LENGTH_LONG)
						.show();
			}
		}

		camera.setParameters(params);

		if (next) {
			camera.takePicture(null, null, this);
		}
	}

	void setDefaultExposureCompensation(int defaultExposureCompensation) {
		this.defaultExposureCompensation = defaultExposureCompensation;
	}

}
