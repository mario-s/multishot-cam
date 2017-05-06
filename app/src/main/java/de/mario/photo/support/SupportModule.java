package de.mario.photo.support;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.glue.BitmapLoadable;
import de.mario.photo.glue.GalleryOpenable;
import de.mario.photo.glue.ImageOpenable;
import de.mario.photo.glue.MediaUpdateable;

/**
 *
 */
@Module
public class SupportModule {

    @Provides
    @Singleton
    public ImageOpenable provideImageOpener(Context context) {
        return new ImageOpener(context);
    }

    @Provides
    @Singleton
    public GalleryOpenable provideGalleryOpener(Context context) {
        return new GalleryOpener(context);
    }

    @Provides
    @Singleton
    public MediaUpdateable provideMediaUpdater(Context context) {
        return new MediaUpdater(context);
    }

    @Provides
    public BitmapLoadable provideBitmapLoader() {
        return new BitmapLoader();
    }
}
