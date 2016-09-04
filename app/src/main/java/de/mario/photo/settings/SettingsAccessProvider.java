package de.mario.photo.settings;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.mario.photo.glue.SettingsAccessable;

/**
 */
class SettingsAccessProvider implements Provider<SettingsAccessable> {

    @Inject
    private Context context;

    @Override
    public SettingsAccessable get() {
        return new SettingsAccess(context);
    }
}
