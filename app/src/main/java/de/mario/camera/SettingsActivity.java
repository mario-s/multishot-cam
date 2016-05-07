package de.mario.camera;

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
        // Get the Preference Category which we want to add the ListPreference to
        PreferenceCategory targetCategory = (PreferenceCategory) findPreference("camera");
        ListPreference customListPref = new ListPreference(this);

        CharSequence[] entries = new CharSequence[]{"One", "Two", "Three"};
        CharSequence[] entryValues = new CharSequence[]{ "1", "2", "3" };

        // IMPORTANT - This is where set entries...looks OK to me
        customListPref.setEntries(entries);
        customListPref.setEntryValues(entryValues);
        customListPref.setTitle("Title");
        customListPref.setSummary("This is the summary");
        customListPref.setPersistent(true);

        // Add the ListPref to the Pref category
        targetCategory.addPreference(customListPref);
    }

}
