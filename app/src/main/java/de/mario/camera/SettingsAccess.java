package de.mario.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static java.lang.Integer.parseInt;

/**
 * This class provides easy access to settings values.
 */
public class SettingsAccess {
    private final Context context;

    public SettingsAccess(Context context) {
        this.context = context;
    }

    public boolean isTrace() { return getBoolean(SettingsValue.TRACE);}

    public String getPicSizeKey() {
        return getPreferences().getString(SettingsValue.PICTURE_SIZE.getValue(), "");
    }

    int getDelay() {return parseInt(getPreferences().getString(SettingsValue.SHUTTER_DELAY.getValue(), "0")); }

    boolean isProcessingEnabled() {return getBoolean(SettingsValue.PROCESS_HDR);}

    boolean isShutterSoundDisabled() {return getBoolean(SettingsValue.SHUTTER_SOUND);}

    boolean isGeoTaggingEnabled() { return getBoolean(SettingsValue.GEO_TAGGING);}

    private boolean getBoolean(SettingsValue key) { return getPreferences().getBoolean(key.getValue(), false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
