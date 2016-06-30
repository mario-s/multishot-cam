package de.mario.photo.controller.shot;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

/**
 * A call back for the picture process.
 */
class DefaultPictureCallback implements PictureCallback {
    /**
     * Default implementation of call back method, which does nothing at all.
     * @param data
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
    }
}
