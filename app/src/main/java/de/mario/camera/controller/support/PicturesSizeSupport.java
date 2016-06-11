package de.mario.camera.controller.support;

import android.hardware.Camera;

import java.util.List;

import de.mario.camera.PhotoActivable;

/**
 * This class provides support for the images sizes supported by the camera.
 */
public final class PicturesSizeSupport {

    private String[] sizes;

    public PicturesSizeSupport(Camera.Parameters params) {
        initSizes(params);
    }

    private void initSizes(Camera.Parameters params) {
        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        sizes = new String[pictureSizes.size()];
        for(int i = 0; i < sizes.length; i++) {
            Camera.Size picSize = pictureSizes.get(i);
            sizes[i] = String.format(PhotoActivable.PIC_SIZE_KEY, picSize.width, picSize.height);
        }
    }

    /**
     * Returns the supported pictures size for the given camera.
     * @return an array of available size as string (width x height)
     */
    public String[] getSupportedPicturesSizes() {
        return sizes;
    }

    /**
     * Return the selected picture size of the given camera.
     * @param camera the camera to use
     * @return size as string
     */
    public String getSelectedPictureSize(Camera camera) {
        return getSelectedPictureSize(camera.getParameters());
    }

    /**
     * Return the selected picture size of the given camera parameters.
     * @param params the parameters to use
     * @return size as string
     */
    public String getSelectedPictureSize(Camera.Parameters params) {
        Camera.Size size = params.getPictureSize();
        return String.format(PhotoActivable.PIC_SIZE_KEY, size.width, size.height);
    }
}
