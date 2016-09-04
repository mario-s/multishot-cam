package de.mario.photo.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.mario.photo.R;
import de.mario.photo.glue.SettingsAccessable;

import static java.lang.Integer.parseInt;

/**
 * This class provides easier access to settings values.
 */
public class SettingsAccess implements SettingsAccessable {

    private Context context;

    public SettingsAccess(Context context){
        this.context = context;
    }

    public boolean isTrace() { return isEnabled(R.string.trace);}

    @Override
    public String getIsoKey() {return getString(Value.ISO_KEY);}

    @Override
    public void setIsoKey(String key) {
        getPreferences().edit().putString(Value.ISO_KEY.getValue(), key).apply();
    }

    @Override
    public String getIsoValue() {return getString(Value.ISO_VALUE);}

    @Override
    public String getPicSizeKey() {return getString(Value.PICTURE_SIZE);}

    @Override
    public int getExposureSequenceType() {return getInt(R.string.evSequence);}

    public int getFlashMode() {
        return getInt(R.string.flashMode);
    }

    @Override
    public boolean isFlashForEach() {return getFlashMode() == 1;}

    @Override
    public boolean isLastFlash() {return getFlashMode() == 2;}

    @Override
    public int getDelay() {return getInt(R.string.shutterDelayTime); }

    public String getString(Value key){return getString(key.getValue());}

    public String getString(int key) {
        return getString(context.getString(key));
    }

    public String getString(String key) {
        return getPreferences().getString(key, "");
    }

    public int getInt(int key){
        return getInt(context.getString(key));
    }

    public int getInt(String key) {
        return parseInt(getPreferences().getString(key, "0"));
    }

    public boolean isEnabled(int key) { return isEnabled(context.getString(key)); }

    public boolean isEnabled(String key) { return getPreferences().getBoolean(key, false); }

    private SharedPreferences getPreferences() {return PreferenceManager.getDefaultSharedPreferences(context);	}
}
