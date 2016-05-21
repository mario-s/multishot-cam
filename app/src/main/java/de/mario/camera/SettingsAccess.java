package de.mario.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static java.lang.Integer.parseInt;

/**
 * This class provides easier access to settings values.
 */
public class SettingsAccess {
    private final Context context;

    public SettingsAccess(Context context) {
        this.context = context;
    }

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

    int getDelay() {return parseInt(getPreferences().getString(SettingsValue.SHUTTER_DELAY.getValue(), "0")); }

    boolean isProcessingEnabled() {return getBoolean(SettingsValue.PROCESS_HDR);}

    boolean isShutterSoundDisabled() {return getBoolean(SettingsValue.SHUTTER_SOUND);}

    boolean isGeoTaggingEnabled() { return getBoolean(SettingsValue.GEO_TAGGING);}

    private String getString(SettingsValue key){return getPreferences().getString(key.getValue(), "");}

    private boolean getBoolean(SettingsValue key) { return getPreferences().getBoolean(key.getValue(), false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
