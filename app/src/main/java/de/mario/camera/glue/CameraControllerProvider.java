package de.mario.camera.glue;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.mario.camera.controller.CameraControlable;
import de.mario.camera.controller.CameraController;
import de.mario.camera.controller.lookup.StorageLookable;

/**
 * Provides an implementation of {@link CameraControlable}
 */
class CameraControllerProvider implements Provider<CameraControlable> {

    @Inject
    private StorageLookable storageLookable;

    @Override
    public CameraControlable get() {
        CameraController controller = new CameraController();
        controller.setStorageLookup(storageLookable);
        return controller;
    }
}
