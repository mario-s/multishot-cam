package de.mario.camera.glue;

import com.google.inject.Provider;

import de.mario.camera.controller.CameraControlable;
import de.mario.camera.controller.CameraController;

/**
 * Provides an implementation of {@link CameraControlable}
 */
public class CameraControllerProvider implements Provider<CameraControlable> {
    @Override
    public CameraControlable get() {
        return new CameraController();
    }
}
