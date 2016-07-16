package de.mario.photo.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.inject.Inject;

import static java.lang.Integer.parseInt;

/**
 * This class provides easier access to settings values.
 */
public class SettingsAccess {
    @Inject
    private Context context;

    public boolean isTrace() { return getBoolean(SettingsValue.TRACE);}

    /**
     * Returns the parameter key to set the ISO value. Use this method to check if the ISo can be
     * change at all. If it is empty it means the device is not supported.
     * @return parameter key as String
     */
    public String getIsoKey() {return getString(SettingsValue.ISO_KEY);}

    /**
     * Set the parameter key to change ISO value for the device.
     * @param key key as String
     */
    public void setIsoKey(String key) {
        getPreferences().edit().putString(SettingsValue.ISO_KEY.getValue(), key).apply();
    }

    /**
     * Returns the currently selected ISO value. Use getIsoKey() before. Some devices might
     * not support ISO settings.
     * @return ISO as String.
     */
    public String getIsoValue() {return getString(SettingsValue.ISO_VALUE);}

    /**
     * Returns the selected size of a picture in the form width x height.
     * @return size as String.
     */
    public String getPicSizeKey() {return getString(SettingsValue.PICTURE_SIZE);}

    public int getExposureSequenceType() {return getInt(SettingsValue.EXPOSURE_SEQ);}

    public int getFlashMode() {
        return getInt(SettingsValue.FLASH);
    }

    /**
     * This methods returns true when for each photo the flash shall be activated.
     * @return boolean
     */
    public boolean isFlashForEach() {return getFlashMode() == 1;}

    /**
     * This methods returns true when the flash shall be activated for the an extra photo at the end.
     * @return boolean
     */
    public boolean isLastFlash() {return getFlashMode() == 2;}

    public int getDelay() {return getInt(SettingsValue.SHUTTER_DELAY); }

    public boolean isProcessingEnabled() {return getBoolean(SettingsValue.PROCESS_HDR);}

    public boolean isShutterSoundDisabled() {return getBoolean(SettingsValue.SHUTTER_SOUND);}

    public boolean isGeoTaggingEnabled() { return getBoolean(SettingsValue.GEO_TAGGING);}

    private String getString(SettingsValue key){return getPreferences().getString(key.getValue(), "");}

    private int getInt(SettingsValue key){ return parseInt(getPreferences().getString(key.getValue(), "0")); }

    private boolean getBoolean(SettingsValue key) { return getPreferences().getBoolean(key.getValue(), false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
