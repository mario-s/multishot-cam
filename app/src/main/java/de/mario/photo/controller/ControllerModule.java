package de.mario.photo.controller;

import android.content.Context;


import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.SettingsAccessable;

/**
 */
@Module
public class ControllerModule {
    private Context context;


    @Provides
    @Singleton
    public CameraControlable provideCameraController(StorageLookable storageLookup, SettingsAccessable settingsAccess) {
        CameraController controller =  new CameraController();
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
    public MediaUpdateController provideMediaUpdateController() {
        return new MediaUpdateController();
    }
}
