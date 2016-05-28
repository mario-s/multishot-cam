package de.mario.camera.callback;

import android.hardware.Camera;

import java.util.LinkedList;

/**
 * Encapsulates the creation of exposure values, used for the photos.
 */
final class ExposureValuesFactory {

    private Camera.Parameters params;

    ExposureValuesFactory(Camera camera) {
        this(camera.getParameters());
    }

    ExposureValuesFactory(Camera.Parameters params) {
        this.params = params;
    }

    LinkedList<Integer> getValues(int seqType) {
        //TODO other sequences
        return getMinMaxValues();
    }

    private LinkedList<Integer> getMinMaxValues() {
        LinkedList<Integer> values = new LinkedList<>();
        values.add(params.getExposureCompensation());
        values.add(params.getMinExposureCompensation());
        values.add(params.getMaxExposureCompensation());
        return values;
    }

}
