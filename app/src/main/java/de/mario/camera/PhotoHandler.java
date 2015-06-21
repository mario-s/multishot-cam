package de.mario.camera;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.os.Environment.DIRECTORY_DCIM;

/**
 * this class takes pictures for each given exposure values and saves those photos.
 *
 * @author Mario
 */
class PhotoHandler implements PictureCallback {

    private static final String JPG = ".jpg";
    private static final String PATTERN = "yyyymmddhhmm";

    private final InternalMemoryAccessor memAccessor;
    private final Queue<Integer> exposureValues;
    private final int maxImages;
    private final int defaultExposure;

    private File pictureFileDir;
    private int imageCounter;
    private List<String> imagesNames = new ArrayList<>();

    private final PhotoActivable activity;

    public PhotoHandler(PhotoActivable activity) {
        this.activity = activity;
        this.exposureValues = activity.getExposureValues();
        this.maxImages = exposureValues.size();
        this.defaultExposure = exposureValues.poll(); //remove the first value and keep it for later
        this.memAccessor = new InternalMemoryAccessor(activity.getApplicationContext());
        this.pictureFileDir = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
    }

    private String getResource(int key) {
        return getContext().getResources().getString(key);
    }

    private Context getContext() {
        return activity.getApplicationContext();
    }

    private void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (!pictureFileDir.exists()) {
            String msg = getResource(R.string.no_directory);
            toast(msg);
            Log.d(PhotoActivable.DEBUG_TAG, msg);
            return;
        }
        showProcessInfo();
        saveInternal(data);
        if (exposureValues.isEmpty()) {
            copyExternal();
        }

        imageCounter++;

        nextPhoto(camera);
    }

    private void showProcessInfo() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                int current = imageCounter + 1;
                toast(String.format(getResource(R.string.photo_taken), current, maxImages));
                Looper.loop();
            }
        }.start();
    }

    private void saveInternal(byte[] data) {
        String name = createFileName();
        try {
            memAccessor.save(data, name);
        } catch (IOException e) {
            Log.e(PhotoActivable.DEBUG_TAG,
                    "File " + name + " not saved: " + e.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private void copyExternal() {

        try {
            String path = pictureFileDir.getAbsolutePath();
            imagesNames.addAll(memAccessor.copyAll(path));
        } catch (IOException exc) {
            Log.e(PhotoActivable.DEBUG_TAG, exc.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private String createFileName() {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        String date = dateFormat.format(new Date());

        StringBuilder builder = new StringBuilder(25);
        builder.append("Picture_").append(date).append("_")
                .append(imageCounter).append(JPG);

        return builder.toString();
    }

    private void nextPhoto(Camera camera) {
        Camera.Parameters params = camera.getParameters();
        if (!exposureValues.isEmpty()) {

            int ev = exposureValues.poll();
            params.setExposureCompensation(ev);
            camera.setParameters(params);
            camera.takePicture(null, null, this);
        } else {
            //reset
            params.setExposureCompensation(defaultExposure);

            //restart preview for next photo
            camera.startPreview();
        }
    }
}
