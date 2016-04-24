package de.mario.camera.callback;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;

import static de.mario.camera.callback.ExposureUpdater.resetExposure;
import static de.mario.camera.callback.ExposureUpdater.setExposureCompensation;

/**
 * This class takes pictures for each given exposure values and saves them.
 *
 * @author Mario
 */
class ContinuesCallback implements PictureCallback {

    private final InternalMemoryAccessor memAccessor;
    private final Entry<String, Integer> [] exposureValues;
    private final int defaultExposure = 0;
    private File pictureFileDir;
    private List<String> imagesNames = new ArrayList<>();
    private final PhotoParams params;
    private int imageCounter;
    private int max;

    ContinuesCallback(PhotoParams params) {
        this.params = params;
        this.exposureValues = params.getExposureEntries();
        this.pictureFileDir = params.getPictureFileDir();
        this.memAccessor = new InternalMemoryAccessor(params.getInternalDirectory());

        imageCounter = 0;
        max = exposureValues.length;
    }

    private String getResource(int key) {
        return params.getResource(key);
    }

    private void toast(final String message) {
        MessageSender sender = new MessageSender(params.getHandler());
        sender.toast(message);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if(imageCounter < max) {
            saveInternal(data);
            imageCounter++;
            nextPhoto(camera);
        }

        if(imageCounter == max){
            moveExternal();
            //reset
            resetExposure(camera);
        }
    }

    private void saveInternal(byte[] data) {

        String name = exposureValues[imageCounter].getKey();
        try {
            memAccessor.save(data, name);
        } catch (IOException e) {
            Log.e(PhotoActivable.DEBUG_TAG,
                    "File " + name + " not saved: " + e.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private void moveExternal() {

        try {
            String path = pictureFileDir.getAbsolutePath();
            imagesNames.addAll(memAccessor.moveAll(path));
            sendFinishedInfo();
        } catch (IOException exc) {
            Log.e(PhotoActivable.DEBUG_TAG, exc.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private void sendFinishedInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.getData().putStringArray(PhotoActivable.PICTURES, imagesNames.toArray(new String[imagesNames.size()]));
                params.getHandler().sendMessage(msg);
            }
        }).start();
    }



    private void nextPhoto(Camera camera) {

        params.getPreview().setEnabled(true);
        camera.startPreview();

        if(imageCounter < max) {
            int ev = exposureValues[imageCounter].getValue();
            setExposureCompensation(camera, ev);
            camera.takePicture(new ShutterCallback(), new LoggingPictureCallback(), this);
        }
    }

}
