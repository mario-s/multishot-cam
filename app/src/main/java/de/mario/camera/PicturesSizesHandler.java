package de.mario.camera;

import android.hardware.Camera;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides support for the images sizes supported by the camera.
 */
class PicturesSizesHandler {
    private static final String KEY_TEMP = "%sx%s";

    private final Map<String, Camera.Size> availableSizes;

    PicturesSizesHandler(Camera camera) {
        availableSizes = new HashMap<>();
        initSizes(camera.getParameters());
    }

    private void initSizes(Camera.Parameters params) {
        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizes) {
            availableSizes.put(String.format(KEY_TEMP, size.width, size.height), size);
        }
    }

    /**
     * Returns the supported pictures size for the given camera.
     * @return an array of available size as string (width x height)
     */
    String[] getSupportedPicturesSizes() {

        String[] sizes = new String[availableSizes.size()];
        return availableSizes.keySet().toArray(sizes);
    }

    /**
     * Return the selected size of the given camera.
     * @param camera the camera to use
     * @return size as string
     */
    String getPictureSize(Camera camera) {
        Camera.Parameters params = camera.getParameters();
        Camera.Size size = params.getPictureSize();
        return String.format(KEY_TEMP, size.width, size.height);
    }

    /**
     * Sets the picture size for the camera
     * @param camera the camera to apply the picture size
     * @param sizeId id of the size (width x height)
     */
    void setPictureSize(Camera camera, String sizeId) {
        if(availableSizes.containsKey(sizeId)) {
            Camera.Size size = availableSizes.get(sizeId);
            Camera.Parameters params = camera.getParameters();
            params.setPictureSize(size.width, size.height);
            camera.setParameters(params);
        }
    }
}
