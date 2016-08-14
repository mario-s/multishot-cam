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

    public enum Value {
        GEO_TAGGING("geotagging"), SHUTTER_SOUND("shutterSound"), PROCESS_HDR("processHdr"),
        NOTIFY_HDR("notifyHdr"),
        SHUTTER_DELAY("shutterDelayTime"), GRID("grid"), TRACE("trace") ,
        PICTURE_SIZE("pictureSize"), ISO_KEY("iso-key"), ISO_VALUE("iso-value"),
        EXPOSURE_SEQ("evSequence"), FLASH("flashMode");

        private String value;

        Value(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private Context context;

    @Inject
    public SettingsAccess(Context context){
        this.context = context;
    }

    public boolean isTrace() { return isEnabled(Value.TRACE);}

    /**
     * Returns the parameter key to set the ISO value. Use this method to check if the ISo can be
     * change at all. If it is empty it means the device is not supported.
     * @return parameter key as String
     */
    public String getIsoKey() {return getString(Value.ISO_KEY);}

    /**
     * Set the parameter key to change ISO value for the device.
     * @param key key as String
     */
    public void setIsoKey(String key) {
        getPreferences().edit().putString(Value.ISO_KEY.getValue(), key).apply();
    }

    /**
     * Returns the currently selected ISO value. Use getIsoKey() before. Some devices might
     * not support ISO settings.
     * @return ISO as String.
     */
    public String getIsoValue() {return getString(Value.ISO_VALUE);}

    /**
     * Returns the selected size of a picture in the form width x height.
     * @return size as String.
     */
    public String getPicSizeKey() {return getString(Value.PICTURE_SIZE);}

    public int getExposureSequenceType() {return getInt(Value.EXPOSURE_SEQ);}

    public int getFlashMode() {
        return getInt(Value.FLASH);
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

    public int getDelay() {return getInt(Value.SHUTTER_DELAY); }

    public boolean isShutterSoundDisabled() {return isEnabled(Value.SHUTTER_SOUND);}

    public boolean isGeoTaggingEnabled() { return isEnabled(Value.GEO_TAGGING);}

    public String getString(Value key){return getPreferences().getString(key.getValue(), "");}

    public int getInt(Value key){ return parseInt(getPreferences().getString(key.getValue(), "0")); }

    public boolean isEnabled(Value key) { return getPreferences().getBoolean(key.getValue(), false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
