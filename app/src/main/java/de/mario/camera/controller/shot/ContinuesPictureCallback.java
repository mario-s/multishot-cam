package de.mario.camera.controller.shot;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Debug;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;


/**
 * This class takes pictures for each given exposure values and saves them.
 *
 * @author Mario
 */
class ContinuesPictureCallback extends DefaultPictureCallback {

    private final InternalMemoryAccessor memAccessor;
    private String [] names;
    private int [] exposures;
    private File pictureFileDir;
    private List<String> imagesNames = new ArrayList<>();
    private final ShotParameters shotParams;
    private int imageCounter;
    private int max;
    private View preview;
    private Camera.ShutterCallback shutterCallback;
    private Camera.PictureCallback pictureCallback;
    private final ParameterUpdater updater;
    //remember the state of the last flash. was it enabled?
    private boolean lastFlash;

    private long start;

    ContinuesPictureCallback(ShotParameters params) {
        this.shotParams = params;
        this.names = params.getNames();
        this.exposures = params.getExposures();
        this.pictureFileDir = params.getPictureFileDir();
        this.memAccessor = new InternalMemoryAccessor(params.getContext());
        this.preview = params.getPreview();
        this.updater = params.getUpdater();

        imageCounter = 0;
        max = exposures.length;

        shutterCallback = new DefaultShutterCallback();
        pictureCallback = new DefaultPictureCallback();

        start = System.currentTimeMillis();
    }

    private String getResource(int key) {
        return shotParams.getResource(key);
    }

    private void toast(final String message) {
        shotParams.toast(message);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if(imageCounter < max) {
            saveInternal(data);
            nextPhoto(camera);
        }

        if(imageCounter == max){
            if(shotParams.isTrace()) {
                long end = System.currentTimeMillis();
                long duration = end - start;
                Log.d(PhotoActivable.DEBUG_TAG, "duration: " + duration);
                Debug.stopMethodTracing();
            }

            moveExternal();

            updater.reset();
            updater.update(camera);
        }
    }

    private void saveInternal(byte[] data) {

        String name = names[imageCounter];
        try {
            memAccessor.save(data, name);
        } catch (IllegalStateException e) {
            Log.e(PhotoActivable.DEBUG_TAG, String.format("File %s not saved: %s", name, e.getMessage()));
            toast(getResource(R.string.save_error));
        }
    }

    private void moveExternal() {

        try {
            String path = pictureFileDir.getAbsolutePath();
            imagesNames.addAll(memAccessor.moveAll(path));
            sendFinishedInfo(path);
        } catch (IOException exc) {
            Log.e(PhotoActivable.DEBUG_TAG, exc.getMessage());
            toast(getResource(R.string.save_error));
        }
    }

    private void sendFinishedInfo(final String path) {
        new ScheduledThreadPoolExecutor(1).execute(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                Bundle bundle = msg.getData();
                bundle.putStringArray(PhotoActivable.PICTURES, imagesNames.toArray(new String[imagesNames.size()]));
                bundle.putString(PhotoActivable.SAVE_FOLDER, path);
                shotParams.getHandler().sendMessage(msg);
            }
        });
    }

    private void nextPhoto(Camera camera) {

        preview.setEnabled(true);
        camera.startPreview();

        imageCounter++;
        if(imageCounter < max) {
            updater.setExposureCompensation(exposures[imageCounter]);

            updateFlash();

            updater.update(camera);

            camera.takePicture(shutterCallback, pictureCallback, this);
        }
    }

    private void updateFlash() {
        boolean flash = shotParams.isFlash(imageCounter);
        if(flash != lastFlash) {
            updater.enableFlash(flash);
        }
        lastFlash = flash;
    }

}
