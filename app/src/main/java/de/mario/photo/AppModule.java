package de.mario.photo;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context application) {
        this.context = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public LocationManager provideLocationManager() {
        return (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    public MyLocationListener provideLocationListener() {
        return new MyLocationListener();
    }

}
