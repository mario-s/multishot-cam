package de.mario.photo.support;

import android.hardware.Camera;

/**
 *
 */
public interface PictureSizeSupportable {

    /**
     * Returns the supported pictures size for the given camera.
     *
     * @return an array of available size as string (width x height)
     */
    String[] getSupportedPicturesSizes();

    /**
     * Return the selected picture size of the given camera.
     *
     * @param camera the camera to use
     * @return size as string
     */
    String getSelectedPictureSize(Camera camera);

}
