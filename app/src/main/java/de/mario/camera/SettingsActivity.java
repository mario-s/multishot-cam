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


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        addImageResolutions();
    }

    private void addImageResolutions() {
        Intent intent = getIntent();
        String [] resolutions = intent.getStringArrayExtra("pictureSizes");
        String selected = intent.getStringExtra("selectedPictureSize");
        // Get the Preference Category which we want to add the ListPreference to
        PreferenceCategory cameraCategory = (PreferenceCategory) findPreference("camera");
        ListPreference customListPref = new ListPreference(this);
        customListPref.setKey(SettingsValue.PICTURE_SIZE.getValue());
        customListPref.setEntries(resolutions);
        customListPref.setEntryValues(resolutions);
        customListPref.setValue(selected);
        customListPref.setTitle(R.string.prefs_picture_size_title);
        customListPref.setSummary(R.string.prefs_picture_size_description);
        customListPref.setPersistent(true);

        // Add the ListPref to the Pref category
        cameraCategory.addPreference(customListPref);
    }

}
