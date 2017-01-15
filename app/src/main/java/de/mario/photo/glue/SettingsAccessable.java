package de.mario.photo.glue;

/**
 */
public interface SettingsAccessable {

    String PICTURE_SIZE = "pictureSize";
    String ISO_KEY = "iso-key";
    String ISO_VALUE = "iso-value";

    /**
     * Returns the selected size of a picture in the form width x height.
     *
     * @return size as String.
     */
    String getPicSizeKey();

    int getExposureSequenceType();

    int getDelay();

    /**
     * This methods returns true when the flash shall be activated for the an extra photo at the end.
     *
     * @return boolean
     */
    boolean isLastFlash();

    /**
     * Returns the parameter key to set the ISO value. Use this method to check if the ISo can be
     * change at all. If it is empty it means the device is not supported.
     *
     * @return parameter key as String
     */
    String getIsoKey();

    /**
     * Set the parameter key to change ISO value for the device.
     *
     * @param key key as String
     */
    void setIsoKey(String key);

    /**
     * Returns the currently selected ISO value. Use getIsoKey() before. Some devices might
     * not support ISO settings.
     *
     * @return ISO as String.
     */
    String getIsoValue();

    /**
     * This methods returns true when for each photo the flash shall be activated.
     *
     * @return boolean
     */
    boolean isFlashForEach();

    boolean isTrace();

    boolean isEnabled(int key);
}
