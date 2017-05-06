package de.mario.photo;

import javax.inject.Singleton;

import dagger.Component;
import de.mario.photo.controller.ControllerModule;
import de.mario.photo.controller.lookup.LookupModule;
import de.mario.photo.settings.SettingsModule;
import de.mario.photo.support.SupportModule;

/**
 *
 */
@Singleton
@Component(modules={
        AppModule.class,
        SettingsModule.class,
        SupportModule.class,
        LookupModule.class,
        ControllerModule.class})
public interface AppComponent {
    void inject(PhotoActivity activity);
}
