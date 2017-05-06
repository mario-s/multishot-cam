package de.mario.photo.support;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class SupportModule {

    @Provides
    @Singleton
    public ImageOpener provideImageOpener(Context context) {
        return new ImageOpener(context);
    }

    @Provides
    @Singleton
    public GalleryOpener provideGalleryOpener(Context context) {
        return new GalleryOpener(context);
    }

    @Provides
    @Singleton
    public MediaUpdater provideMediaUpdater(Context context) {
        return new MediaUpdater(context);
    }

    @Provides
    public BitmapLoader provideBitmapLoader() {
        return new BitmapLoader();
    }
}
