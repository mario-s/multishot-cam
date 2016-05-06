package de.mario.camera.callback;

import android.hardware.Camera;

/**
 * Updates the el values of the camera.
 */
final class ExposureUpdater {
    private final Camera.Parameters params;

    ExposureUpdater(Camera.Parameters params) {
        this.params = params;
    }

    ExposureUpdater(Camera camera){
        params = camera.getParameters();
    }

    void resetExposure(Camera camera) {
        setExposureCompensation(camera, 0);
    }

    void setExposureCompensation(Camera camera, int ev) {
        params.setExposureCompensation(ev);
        camera.setParameters(params);
    }
}
