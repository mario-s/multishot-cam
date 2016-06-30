package de.mario.photo.controller;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.mario.photo.controller.lookup.StorageLookup;

/**
 */
class StorageLookupProvider implements Provider<StorageLookup>{

    @Inject
    private Context context;

    @Override
    public StorageLookup get() {
        return new StorageLookup(context);
    }
}
