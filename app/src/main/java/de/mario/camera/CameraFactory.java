package de.mario.camera;


import android.hardware.Camera;
import android.util.Log;

/**
 * Class to open the camera.
 */
final class CameraFactory {

    private CameraFactory(){}

    public static Camera getCamera(int id) {
        Camera c = null;
        try {
            c = Camera.open(id);
        }catch (Exception e){
            Log.w(PhotoActivable.DEBUG_TAG, e);
        }
        return c;
    }
}
