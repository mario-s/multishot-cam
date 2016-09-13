package de.mario.photo.glue;

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
     * @return size as string
     */
    String getSelectedPictureSize();

}
