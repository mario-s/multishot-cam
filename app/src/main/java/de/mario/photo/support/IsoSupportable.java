package de.mario.photo.support;

/**
 *
 */
public interface IsoSupportable {


    /**
     * Returns a list of support ISO values for this device. The array can also be empty.
     *
     * @return an array of string
     */
    String[] getIsoValues();


    String getSelectedIsoValue(String isoKey);

    /**
     * This method tries to look for a parameter key to get the selected ISO value for the device.
     *
     * @return the key as a String or a empty String if it is not supported
     */
    String findIsoKey();
}
