package de.mario.camera.callback;

import android.hardware.Camera;

import de.mario.camera.PhotoActivable;

/**
 * Command which will be executed to take pictures.
 */
public class PhotoCommand implements Runnable{

    private final PhotoActivable activity;
    private final Camera camera;

    public PhotoCommand(PhotoActivable activity, Camera camera){
        this.activity = activity;
        this.camera = camera;
    }

    @Override
    public void run() {
        ContinuesCallback callback = new ContinuesCallback(activity);
        camera.takePicture(new ShutterCallback(), new LoggingPictureCallback(), callback);
    }
}
