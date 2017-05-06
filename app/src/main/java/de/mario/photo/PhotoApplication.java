package de.mario.photo;

import android.app.Application;

/**
 *
 */

public class PhotoApplication extends Application{

    private static PhotoApplication application;

    private AppComponent appComponent;



    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public static PhotoApplication getApplication() {
        return application;
    }
}
