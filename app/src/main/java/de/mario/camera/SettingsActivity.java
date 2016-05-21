package de.mario.camera;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {


    public static final String CAMERA = "camera";

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Intent intent = getIntent();
        addImageResolutions(intent);
        addIsos(intent);
    }

    private void addImageResolutions(Intent intent) {
        String [] resolutions = intent.getStringArrayExtra("pictureSizes");
        String selected = intent.getStringExtra("selectedPictureSize");

        ListPreference customListPref = createListPreference(resolutions, selected);
        customListPref.setKey(SettingsValue.PICTURE_SIZE.getValue());
        customListPref.setTitle(R.string.prefs_picture_size_title);
        customListPref.setSummary(R.string.prefs_picture_size_description);

        addPreference(customListPref);
    }

    private void addIsos(Intent intent) {
        String selected = intent.getStringExtra("selectedIso");
        if(selected != null) {
            String[] isos = intent.getStringArrayExtra("isos");


            ListPreference customListPref = createListPreference(isos, selected);
            customListPref.setKey(SettingsValue.ISO_VALUE.getValue());
            customListPref.setTitle(R.string.prefs_iso_title);
            customListPref.setSummary(R.string.prefs_iso_description);

            addPreference(customListPref);
        }
    }

    private ListPreference createListPreference(String [] available, String selected) {
        ListPreference customListPref = new ListPreference(this);
        customListPref.setPersistent(true);
        customListPref.setEntries(available);
        customListPref.setEntryValues(available);
        customListPref.setValue(selected);
        return customListPref;
    }

    private void addPreference(ListPreference customListPref) {
        PreferenceCategory cameraCategory = (PreferenceCategory) findPreference(CAMERA);
        cameraCategory.addPreference(customListPref);
    }

}
