package de.mario.photo.controller.shot;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mario.photo.R;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.glue.TaskListener;
import de.mario.photo.support.HandlerThreadFactory;
import roboguice.util.Ln;


/**
 * This class takes pictures for each given exposure values and saves them.
 *
 * @author Mario
 */
class ContinuesPictureCallback extends DefaultPictureCallback implements TaskListener {

    private final RawDataIO io;
    private final ShotParameters shotParams;
    private final ParameterUpdater updater;
    private String [] names;
    private int [] exposures;
    private File pictureFileDir;
    private List<String> imagesNames = new ArrayList<>();
    private int imageCounter;
    private int max;
    private View preview;
    private Camera.ShutterCallback shutterCallback;
    private Camera.PictureCallback pictureCallback;
    private Handler handler;
    //remember the state of the last flash. was it enabled?
    private boolean lastFlash;

    private long start;

    ContinuesPictureCallback(ShotParameters params) {
        this.shotParams = params;
        this.names = params.getNames();
        this.exposures = params.getExposures();
        this.pictureFileDir = params.getPictureFileDir();
        this.io = new RawDataIO(params.getContext(), this);
        this.preview = params.getPreview();
        this.updater = params.getUpdater();

        imageCounter = 0;
        max = exposures.length;

        shutterCallback = new DefaultShutterCallback();
        pictureCallback = new DefaultPictureCallback();

        HandlerThreadFactory factory = new HandlerThreadFactory(getClass());
        handler = factory.newHandler();

        start = System.currentTimeMillis();
    }

    private String getResource(int key) {
        return shotParams.getResource(key);
    }

    private void send(final String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        shotParams.send(msg);
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
                Ln.d("duration: %s", duration);
                Debug.stopMethodTracing();
            }
            updater.reset();
            updater.update(camera);
        }
    }

    private void saveInternal(byte[] data) {
        String name = names[imageCounter];
        try {
            io.save(data, name);
        } catch (IllegalStateException e) {
            Ln.w(e, "File %s not saved: %s", name, e.getMessage());
            send(getResource(R.string.save_error));
        }
    }

    @Override
    public void onFinish(Object param) {
        if(imageCounter == max) {
            moveExternal();
        }
    }

    private void moveExternal() {
        try {
            String path = pictureFileDir.getAbsolutePath();
            imagesNames.addAll(io.moveAll(path));
            sendFinishedInfo(path);
        } catch (IOException exc) {
            Ln.w(exc, exc.getMessage());
            send(getResource(R.string.save_error));
        }
    }

    private void sendFinishedInfo(final String path) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                Bundle bundle = msg.getData();
                bundle.putStringArray(PhotoActivable.PICTURES, imagesNames.toArray(new String[imagesNames.size()]));
                bundle.putString(PhotoActivable.SAVE_FOLDER, path);
                shotParams.send(msg);
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
        if (flash != lastFlash) {
            updater.enableFlash(flash);
        }
        lastFlash = flash;
    }

}
