package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import de.mario.camera.PhotoActivable;

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
