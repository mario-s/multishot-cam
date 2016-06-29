package de.mario.camera.glue;

import com.google.inject.AbstractModule;

import de.mario.camera.controller.CameraControlable;
import de.mario.camera.controller.lookup.StorageLookable;

/**
 */
public class DefaultModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(StorageLookable.class).toProvider(StorageLookupProvider.class);
        bind(CameraControlable.class).toProvider(CameraControllerProvider.class);
    }
}
