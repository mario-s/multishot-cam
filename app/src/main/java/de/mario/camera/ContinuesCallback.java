package de.mario.camera;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Message;
import android.util.Log;


/**
 * This class takes pictures for each given exposure values and saves them.
 *
 * @author Mario
 */
class ContinuesCallback implements PictureCallback {

    private static final String JPG = ".jpg";
    private static final String PATTERN = "yyyymmddhhmm";

    private final InternalMemoryAccessor memAccessor;
    private final Queue<Integer> exposureValues;
    private final int defaultExposure;

    private File pictureFileDir;
    private int imageCounter;
    private List<String> imagesNames = new ArrayList<>();

    private final PhotoActivable activity;

    public ContinuesCallback(PhotoActivable activity) {
        this.activity = activity;
        this.exposureValues = activity.getExposureValues();
        this.defaultExposure = exposureValues.poll(); //remove the first value and keep it for later
        this.memAccessor = new InternalMemoryAccessor(activity.getInternalDirectory());
        this.pictureFileDir = activity.getPicturesDirectory(); //image path on external storage
    }

    private String getResource(int key) {
        return activity.getResource(key);
    }

    private void toast(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = message;
                activity.getHandler().sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (!pictureFileDir.exists()) {
            String msg = getResource(R.string.no_directory);
            toast(msg);
            Log.d(PhotoActivable.DEBUG_TAG, msg);
            return;
        }
        saveInternal(data);
        if (exposureValues.isEmpty()) {
            copyExternal();
        }

        imageCounter++;

        nextPhoto(camera);
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
            imagesNames.addAll(memAccessor.moveAll(path));
            showSuccessInfo();
        } catch (IOException exc) {
            Log.e(PhotoActivable.DEBUG_TAG, exc.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private void showSuccessInfo() {
        int current = imageCounter + 1;
        toast(String.format(getResource(R.string.photos_saved), current, pictureFileDir));
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
        camera.setParameters(params);
        if (!exposureValues.isEmpty()) {

            int ev = exposureValues.poll();
            params.setExposureCompensation(ev);

            camera.takePicture(null, null, this);
        } else {
            //reset
            params.setExposureCompensation(defaultExposure);

            //restart preview for next photo
            camera.startPreview();
        }
    }
}
