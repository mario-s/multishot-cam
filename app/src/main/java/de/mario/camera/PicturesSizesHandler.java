package de.mario.camera;

import android.hardware.Camera;

import java.util.List;

/**
 * This class provides support for the images sizes supported by the camera.
 */
class PicturesSizesHandler {

    /**
     * Returns the supported pictures size for the given camera.
     * @param camera
     * @return
     */
    String[] getSupportedPicturesSizes(Camera camera) {
        Camera.Parameters params = camera.getParameters();

        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        String[] sizes = new String[pictureSizes.size()];
        for (int i = 0; i < sizes.length; i++) {
            Camera.Size picSize = pictureSizes.get(i);
            sizes[i] = picSize.width + "x" + picSize.height;
        }

        return sizes;
    }
}
