package de.mario.photo.controller.lookup;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class LookupModule {

    @Provides
    @Singleton
    public StorageLookable provideSorageLookup(Context context) {
        return new StorageLookup(context);
    }
}
