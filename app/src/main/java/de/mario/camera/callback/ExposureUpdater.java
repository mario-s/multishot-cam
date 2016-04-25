package de.mario.camera.callback;

import android.hardware.Camera;

/**
 * Updates the el values of the camera.
 */
final class ExposureUpdater {
    private ExposureUpdater(){}

    static void resetExposure(Camera camera) {
        setExposureCompensation(camera, 0);
    }

    static void setExposureCompensation(Camera camera, int ev) {
        Camera.Parameters params = camera.getParameters();
        params.setExposureCompensation(ev);
        camera.setParameters(params);
    }
}
