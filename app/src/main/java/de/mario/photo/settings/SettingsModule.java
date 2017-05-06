package de.mario.photo.settings;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.glue.SettingsAccessable;


/**
 */
@Module
public class SettingsModule {

    @Provides
    @Singleton
    public SettingsAccessable provideSettingsAccess(Context context) {
        return new SettingsAccess(context);
    }
}
