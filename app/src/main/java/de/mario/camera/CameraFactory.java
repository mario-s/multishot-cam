package de.mario.camera;


import android.hardware.Camera;
import android.util.Log;

/**
 * Class to open the camera.
 */
final class CameraFactory {

    Camera getCamera(int id) {
        Camera cam = null;
        try {
            cam = open(id);

            Camera.Parameters params = cam.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }catch (Exception e){
            Log.w(PhotoActivable.DEBUG_TAG, e);
        }
        return cam;
    }

    Camera open(int id) {
        return Camera.open(id);
    }
}
