package de.mario.camera.callback;

import android.hardware.Camera;

/**
 * Updates the parameters of the camera without getting them each time a single parameter
 * is adjusted.
 */
final class ParameterUpdater {
    private final Camera.Parameters params;

    ParameterUpdater(Camera.Parameters params) {
        this.params = params;
    }

    ParameterUpdater(Camera camera){
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
