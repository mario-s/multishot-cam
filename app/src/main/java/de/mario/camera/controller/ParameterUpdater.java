package de.mario.camera.controller;

import android.hardware.Camera;

import java.util.List;

import de.mario.camera.PhotoActivable;

/**
 * Updates the parameters of the camera without getting them each time a single parameter
 * is adjusted.
 */
final class ParameterUpdater {
    private final Camera.Parameters params;

    ParameterUpdater(Camera camera){
        this(camera.getParameters());
    }

    ParameterUpdater(Camera.Parameters params) {
        this.params = params;
    }

    /**
     * Sets the picture size for the camera
     * @param sizeId id of the size (width x height)
     */
    void setPictureSize(String sizeId) {
        if(!sizeId.isEmpty()) {
            List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
            for (Camera.Size size : pictureSizes) {
                String id = String.format(PhotoActivable.PIC_SIZE_KEY, size.width, size.height);
                if (sizeId.equals(id)){
                    params.setPictureSize(size.width, size.height);
                    break;
                }
            }
        }
    }

    void setIso(String key, String value) {
        if(!key.isEmpty() && !value.isEmpty()) {
            params.set(key, value);
        }
    }

    void resetExposure() {
        setExposureCompensation(0);
    }

    void setExposureCompensation(int ev) {
        params.setExposureCompensation(ev);
    }

    void update(Camera camera){
        camera.setParameters(params);
    }
}
