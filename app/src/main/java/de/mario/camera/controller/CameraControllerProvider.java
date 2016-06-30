package de.mario.camera.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.mario.camera.controller.lookup.StorageLookable;

/**
 * Provides an implementation of {@link CameraControlable}
 */
class CameraControllerProvider implements Provider<CameraControlable> {

    @Inject
    private StorageLookable storageLookable;

    @Override
    public CameraControlable get() {
        CameraController controller = newController();
        controller.setStorageLookup(storageLookable);
        return controller;
    }

    CameraController newController() {
        return new CameraController();
    }
}
