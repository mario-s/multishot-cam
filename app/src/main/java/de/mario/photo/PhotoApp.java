package de.mario.photo;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import de.mario.photo.controller.ControllerModule;
import de.mario.photo.controller.lookup.LookupModule;
import de.mario.photo.settings.SettingsModule;
import de.mario.photo.support.SupportModule;

/**
 *
 */

public class PhotoApp extends Application {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .controllerModule(new ControllerModule())
                .lookupModule(new LookupModule())
                .settingsModule(new SettingsModule())
                .supportModule(new SupportModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
