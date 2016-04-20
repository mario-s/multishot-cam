package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import de.mario.camera.PhotoActivable;

/**
 * A call back which just logs the process
 */
class LoggingPictureCallback implements Camera.PictureCallback {
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(PhotoActivable.DEBUG_TAG, "onPictureTaken");
    }
}
