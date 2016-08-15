package de.mario.photo.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.controller.lookup.StorageLookupProvider;

/**
 */
public class ControllerModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(StorageLookable.class).toProvider(StorageLookupProvider.class).in(Singleton.class);
        bind(CameraControlable.class).toProvider(CameraControllerProvider.class).in(Singleton.class);
        bind(HdrProcessControlable.class).to(HdrProcessController.class).in(Singleton.class);
    }
}
