package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import de.mario.camera.PhotoActivable;

/**
 * Callback for Shutter.
 */
class ShutterCallback implements Camera.ShutterCallback {
    @Override
    public void onShutter() {
        Log.d(PhotoActivable.DEBUG_TAG, "onShutter");
    }
}
