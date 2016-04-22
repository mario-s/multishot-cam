package de.mario.camera;

import android.hardware.Camera;

import java.util.LinkedList;

/**
 * Encapsulates the creation of exposure values.
 */
final class ExposureValuesFactory {

    private Camera camera;

    ExposureValuesFactory(Camera camera) {
        this.camera = camera;
    }

    LinkedList<Integer> getMinMaxValues() {
        LinkedList<Integer> values = new LinkedList<>();
        Camera.Parameters params = camera.getParameters();
        values.add(params.getExposureCompensation());
        values.add(params.getMinExposureCompensation());
        values.add(params.getMaxExposureCompensation());
        return values;
    }

}
