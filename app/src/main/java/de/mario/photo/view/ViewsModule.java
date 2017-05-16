package de.mario.photo.view;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.SettingsAccessable;
import de.mario.photo.glue.ViewsMediatable;

/**
 *
 */
@Module
public class ViewsModule {

    @Provides
    @Singleton
    public ViewsMediatable provideViewsMediator(SettingsAccessable settingsAccess, CameraControlable cameraController,
                                                GridView gridView, LevelView levelView) {
        return new ViewsMediator(settingsAccess, cameraController, gridView, levelView);
    }
}
