package de.mario.camera;

import android.hardware.Camera;

/**
 * Command which will be executed to take pictures.
 */
class PhotoCommand implements Runnable{

    private final PhotoActivable activity;
    private final Camera camera;

    PhotoCommand(PhotoActivable activity, Camera camera){
        this.activity = activity;
        this.camera = camera;
    }

    @Override
    public void run() {
        ContinuesCallback callback = new ContinuesCallback(activity);
        camera.takePicture(null, null, callback);
    }
}
