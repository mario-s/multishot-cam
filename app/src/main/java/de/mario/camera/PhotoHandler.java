package de.mario.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private final InternalMemoryAccessor memAccessor;

	private File pictureFileDir;
	private int imageCounter;
	private int defaultExposureCompensation;
	private List<String> internalNames = new ArrayList<String>(REQ_IMAGES);
	private List<String> imagesNames = new ArrayList<String>(REQ_IMAGES);

	public PhotoHandler(Context context) {
		this(0, context);
	}

	public PhotoHandler(int imageCounter, Context context) {
		this.imageCounter = imageCounter;
		this.context = context;
		this.memAccessor = new InternalMemoryAccessor(context);
		pictureFileDir = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		if (!pictureFileDir.exists()) {

			Log.d(PhotoActivity.DEBUG_TAG, NO_DIR);
			Toast.makeText(context, NO_DIR, Toast.LENGTH_LONG).show();
			return;
		}

		saveInternal(data);
		if (imageCounter == REQ_IMAGES - 1) {
			copyExternal();
		}

		imageCounter++;

		nextPhoto(camera);
	}

	private void saveInternal(byte[] data) {
		String name = createFileName();
		try {
			memAccessor.save(data, name);
			internalNames.add(name);
		} catch (IOException e) {
			logException(name, e);
		}
	}

	private void copyExternal() {
		for(String internal : internalNames){
			try {
				copyExternal(memAccessor.load(internal), internal);
			} catch (IOException e) {
				logException(internal, e);
			}
		}
	}

	private void copyExternal(byte[] data, String name) {
		try {
			File pictureFile = new File(pictureFileDir, name);
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			imagesNames.add(pictureFile.getPath());
		} catch (Exception exc) {
			logException(name, exc);
		}
	}

	private void logException(String name, Exception exc) {
		Log.e(PhotoActivity.DEBUG_TAG,
				"File" + name + "not saved: " + exc.getMessage());
		Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_LONG)
				.show();
	}

	private String createFileName() {
		DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmm");
		String date = dateFormat.format(new Date());

		StringBuilder builder = new StringBuilder(25);
		builder.append("Picture_").append(date).append("_")
				.append(imageCounter).append(JPG);

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
				Toast.makeText(context, "New Images saved to: " + pictureFileDir, Toast.LENGTH_LONG)
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
