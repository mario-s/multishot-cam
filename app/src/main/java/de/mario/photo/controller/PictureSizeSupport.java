package de.mario.photo.controller;

import android.hardware.Camera;

import java.util.Arrays;
import java.util.List;

import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.glue.PictureSizeSupportable;

/**
 * This class provides support for the images sizes supported by the camera.
 */
public final class PictureSizeSupport implements PictureSizeSupportable {

    private String[] sizes = new String[0];

    private Camera.Parameters parameters;

    public PictureSizeSupport(Camera.Parameters parameters) {
        this.parameters = parameters;
        initSizes();
    }

    private void initSizes() {
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        sizes = new String[pictureSizes.size()];
        for(int i = 0; i < sizes.length; i++) {
            Camera.Size picSize = pictureSizes.get(i);
            sizes[i] = String.format(PhotoActivable.PIC_SIZE_KEY, picSize.width, picSize.height);
        }
    }

    @Override
    public String[] getSupportedPicturesSizes() {
        return Arrays.copyOf(sizes, sizes.length);
    }

    /**
     * Return the selected picture size of the given camera parameters.
     * @param params the parameters to use
     * @return size as string
     */
    String getSelectedPictureSize(Camera.Parameters params) {
        Camera.Size size = params.getPictureSize();
        return String.format(PhotoActivable.PIC_SIZE_KEY, size.width, size.height);
    }

    @Override
    public String getSelectedPictureSize() {
        return getSelectedPictureSize(parameters);
    }
}
