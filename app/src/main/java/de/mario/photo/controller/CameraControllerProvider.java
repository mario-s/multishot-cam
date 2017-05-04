package de.mario.photo.controller;

import com.google.inject.Provider;

import javax.inject.Inject;

import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.SettingsAccessable;

/**
 * Provides an implementation of {@link CameraControlable}
 */
class CameraControllerProvider implements Provider<CameraControlable> {

    @Inject
    private StorageLookable storageLookable;

    @Inject
    private SettingsAccessable settingsAccess;

    @Override
    public CameraControlable get() {
        CameraController controller = newController();
        controller.setSettingsAccess(settingsAccess);
        controller.setStorageLookup(storageLookable);
        return controller;
    }

    CameraController newController() {
        return new CameraController();
    }
}
