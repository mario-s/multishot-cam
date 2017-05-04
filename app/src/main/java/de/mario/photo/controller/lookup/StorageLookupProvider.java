package de.mario.photo.controller.lookup;

import android.content.Context;

import javax.inject.Inject;
import com.google.inject.Provider;

/**
 */
public class StorageLookupProvider implements Provider<StorageLookup> {

    @Inject
    private Context context;

    @Override
    public StorageLookup get() {
        return new StorageLookup(context);
    }
}
