package de.mario.camera.controller;

import android.hardware.Camera;

/**
 * A call back for the picture process.
 */
class DefaultPictureCallback implements Camera.PictureCallback {
    /**
     * Default implementation of call back method, which does nothing at all.
     * @param data
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
    }
}
