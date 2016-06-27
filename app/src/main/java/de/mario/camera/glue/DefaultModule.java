package de.mario.camera.glue;

import com.google.inject.AbstractModule;

import de.mario.camera.controller.CameraControlable;

/**
 */
public class DefaultModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(CameraControlable.class).toProvider(CameraControllerProvider.class);
    }
}
