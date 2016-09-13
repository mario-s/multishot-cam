package de.mario.photo.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;

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
        AbstractCameraController controller = newController();
        controller.setSettingsAccess(settingsAccess);
        controller.setStorageLookup(storageLookable);
        return controller;
    }

    AbstractCameraController newController() {
        return new CameraApi1Controller();
    }
}
