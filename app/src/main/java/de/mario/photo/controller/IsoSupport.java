package de.mario.photo.controller;

import android.hardware.Camera;

import de.mario.photo.glue.IsoSupportable;

/**
 * This class handles the ISO support. However there is no standard in the Android API.
 *
 * @see http://stackoverflow.com/questions/2978095/android-camera-api-iso-setting.
 */
public final class IsoSupport implements IsoSupportable {
    private static final String[] ISO_KEYS = new String[]{"iso", "iso-mode", "iso-speed", "nv-picture-iso"};

    private static final String[] ISO_VALUES_KEYS = new String[]{"iso-values", "iso-mode-values", "iso-speed-values", "nv-picture-iso-values"};

    private static final String[] DEFAULT_ISOS = new String[]{"auto", "100", "200", "400", "800", "1600"};

    private final Camera.Parameters params;

    public IsoSupport(Camera.Parameters params) {
        this.params = params;
    }

    @Override
    public String[] getIsoValues() {
        String[] isos;
        String isoValues = findIsoValues();
        if (isoValues != null && !isoValues.isEmpty()) {
            isos = isoValues.split(",");
        }else{
            isos = DEFAULT_ISOS;
        }
        return isos;
    }

    /**
     * Try to look for available ISO values
     *
     * @return either iso values or null
     */
    private String findIsoValues() {

        for (String key: ISO_VALUES_KEYS) {
            String vals = params.get(key);
            if(vals != null){
                return vals;
            }
        }
        return null;
    }

    @Override
    public String getSelectedIsoValue(String isoKey) {

        if(isoKey != null){
            return params.get(isoKey);
        }
        return null;
    }

    @Override
    public String findIsoKey() {
        String isoKey = "";
        for(String key : ISO_KEYS) {
            if( params.get(key) != null ){
                isoKey = key;
                break;
            }
        }
        return isoKey;
    }
}
