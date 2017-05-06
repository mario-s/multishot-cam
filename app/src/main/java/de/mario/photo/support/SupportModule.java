package de.mario.photo.support;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.glue.BitmapLoadable;

/**
 *
 */
@Module
public class SupportModule {

    @Provides
    public BitmapLoadable provideBitmapLoader() {
        return new BitmapLoader();
    }
}
