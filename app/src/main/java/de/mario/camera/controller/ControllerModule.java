package de.mario.camera.controller;

import com.google.inject.AbstractModule;

import de.mario.camera.controller.lookup.StorageLookable;

/**
 */
public class ControllerModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(StorageLookable.class).toProvider(StorageLookupProvider.class);
        bind(CameraControlable.class).toProvider(CameraControllerProvider.class);
    }
}
