package de.mario.photo.settings;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import de.mario.photo.BuildConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Integration Test for {@link SettingsActivity} and {@link SettingsAccess}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class SettingsAccessITest {

    private SettingsAccess classUnderTest;

    @Before
    public void setUp() {
        SettingsActivity activity = Robolectric.buildActivity(SettingsActivity.class).create().get();
        Bundle bundle = new Bundle();
        activity.onPostCreate(bundle);

        classUnderTest = new SettingsAccess(activity.getBaseContext());
    }

    @Test
    public void testDelay() {
        assertThat(classUnderTest.getDelay(), is(0));
    }

    @Test
    public void testIsGeoTagEnabled() {
        assertThat(classUnderTest.isGeoTaggingEnabled(), is(false));
    }
}
