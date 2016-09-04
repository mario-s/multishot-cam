package de.mario.photo.settings;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.mario.photo.glue.SettingsAccessable;


/**
 */
public class SettingsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SettingsAccessable.class).toProvider(SettingsAccessProvider.class).in(Singleton.class);
    }
}
