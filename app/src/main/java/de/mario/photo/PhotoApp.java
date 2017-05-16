package de.mario.photo;

import android.app.Application;

import de.mario.photo.controller.ControllerModule;
import de.mario.photo.controller.lookup.LookupModule;
import de.mario.photo.settings.SettingsModule;
import de.mario.photo.support.SupportModule;
import de.mario.photo.view.ViewsModule;

/**
 *
 */

public class PhotoApp extends Application {

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
                .viewsModule(new ViewsModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
