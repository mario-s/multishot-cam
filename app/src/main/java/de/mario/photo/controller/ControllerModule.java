package de.mario.photo.controller;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.glue.BitmapLoadable;
import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.GalleryOpenable;
import de.mario.photo.glue.ImageOpenable;
import de.mario.photo.glue.MediaUpdateControlable;
import de.mario.photo.glue.MediaUpdateable;
import de.mario.photo.glue.SettingsAccessable;

/**
 */
@Module
public class ControllerModule {

    @Provides
    @Singleton
    public CameraControlable provideCameraController(StorageLookable storageLookup, SettingsAccessable settingsAccess) {
        CameraController controller = new CameraController();
        controller.setStorageLookup(storageLookup);
        controller.setSettingsAccess(settingsAccess);
        return controller;
    }

    @Provides
    @Singleton
    public HdrProcessControlable provideHdrProcessController(Context context) {
        return new HdrProcessController(context);
    }

    @Provides
    @Singleton
    public MediaUpdateControlable provideMediaUpdateController(MediaUpdateable mediaUpdater,
                                                               ImageOpenable imageOpener,
                                                               GalleryOpenable galleryOpener,
                                                               BitmapLoadable bitmapLoader) {
        MediaUpdateController controller = new MediaUpdateController();
        controller.setMediaUpdater(mediaUpdater);
        controller.setImageOpener(imageOpener);
        controller.setGalleryOpener(galleryOpener);
        controller.setBitmapLoader(bitmapLoader);
        controller.initialize();
        return controller;
    }
}
