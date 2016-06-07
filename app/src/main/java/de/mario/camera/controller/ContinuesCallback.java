package de.mario.camera.controller;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
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
class ContinuesCallback implements PictureCallback {

    private final InternalMemoryAccessor memAccessor;
    private String [] names;
    private int [] exposures;
    private final int defaultExposure = 0;
    private File pictureFileDir;
    private List<String> imagesNames = new ArrayList<>();
    private final ShotParams params;
    private int imageCounter;
    private int max;
    private View preview;
    private Camera.ShutterCallback shutterCallback;
    private Camera.PictureCallback pictureCallback;
    private final ParameterUpdater updater;

    private long start;

    ContinuesCallback(ShotParams params) {
        this.params = params;
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
            nextPhoto(camera);
        }

        if(imageCounter == max){
            if(params.isTrace()) {
                long end = System.currentTimeMillis();
                long duration = end - start;
                Log.d(PhotoActivable.DEBUG_TAG, "duration: " + duration);
                Debug.stopMethodTracing();
            }

            moveExternal();

            updater.resetExposure();
            updater.update(camera);
        }
    }

    private void saveInternal(byte[] data) {

        String name = names[imageCounter];
        try {
            memAccessor.save(data, name);
        } catch (IllegalStateException e) {
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
        new ScheduledThreadPoolExecutor(1).execute(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.getData().putStringArray(PhotoActivable.PICTURES, imagesNames.toArray(new String[imagesNames.size()]));
                params.getHandler().sendMessage(msg);
            }
        });
    }

    private void nextPhoto(Camera camera) {

        preview.setEnabled(true);
        camera.startPreview();

        imageCounter++;
        if(imageCounter < max) {
            updater.setExposureCompensation(exposures[imageCounter]);
            updater.update(camera);

            camera.takePicture(shutterCallback, pictureCallback, this);
        }
    }

}