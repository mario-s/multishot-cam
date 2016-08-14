package de.mario.photo.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.inject.Inject;

import de.mario.photo.R;

import static java.lang.Integer.parseInt;

/**
 * This class provides easier access to settings values.
 */
public class SettingsAccess {

    public enum Value {
        PICTURE_SIZE("pictureSize"), ISO_KEY("iso-key"), ISO_VALUE("iso-value");

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

    public boolean isTrace() { return isEnabled(R.string.trace);}

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

    public int getExposureSequenceType() {return getInt(R.string.evSequence);}

    public int getFlashMode() {
        return getInt(R.string.flashMode);
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

    public int getDelay() {return getInt(R.string.shutterDelayTime); }

    public String getString(Value key){return getString(key.getValue());}

    public String getString(int key) {
        return getString(context.getString(key));
    }

    public String getString(String key) {
        return getPreferences().getString(key, "");
    }

    public int getInt(Value key){ return getInt(key.getValue()); }

    public int getInt(int key){
        return getInt(context.getString(key));
    }

    public int getInt(String key) {
        return parseInt(getPreferences().getString(key, "0"));
    }

    public boolean isEnabled(int key) { return isEnabled(context.getString(key)); }

    public boolean isEnabled(Value key) { return isEnabled(key.getValue()); }

    public boolean isEnabled(String key) { return getPreferences().getBoolean(key, false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
