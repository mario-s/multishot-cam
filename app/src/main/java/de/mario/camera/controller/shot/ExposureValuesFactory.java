package de.mario.camera.controller.shot;

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
        switch(seqType){
            case 1:
                return minToMaxIn2El();
            default:
                return minNormMax();
        }
    }

    private LinkedList<Integer> minToMaxIn2El() {
        LinkedList<Integer> values = new LinkedList<>();

        int min = params.getMinExposureCompensation();
        int max = params.getMaxExposureCompensation();
        for(int i = min; i <= max; i = i+2){
           values.add(i);
        }

        return values;
    }

    private LinkedList<Integer> minNormMax() {
        LinkedList<Integer> values = new LinkedList<>();
        values.add(params.getMinExposureCompensation());
        values.add(params.getExposureCompensation());
        values.add(params.getMaxExposureCompensation());
        return values;
    }

}
