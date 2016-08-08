package de.mario.photo.controller;


import android.hardware.Camera;

import roboguice.util.Ln;

/**
 * Class to open the camera and get required features.
 */
class CameraFactory {

    /**
     * Opens the camera identified by the ID.
     * @param id
     * @return
     */
    Camera getCamera(int id) {
        Camera cam = null;
        try {
            cam = open(id);

            Camera.Parameters params = cam.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            cam.setParameters(params);
        }catch (Exception e){
            Ln.w(e, e.getMessage());
        }
        return cam;
    }

    Camera open(int id) {
        return Camera.open(id);
    }
}
