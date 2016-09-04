package de.mario.photo.view;

import android.content.Context;

import de.mario.photo.glue.SettingsAccessable;
import de.mario.photo.settings.SettingsAccess;

/**
 */
abstract class AbstractSettingsAccessPaintView extends AbstractPaintView {

    protected final SettingsAccessable settingsAccess;

    public AbstractSettingsAccessPaintView(Context context) {
        super(context);
        //TODO don't use impl
        this.settingsAccess = new SettingsAccess(context);
    }
}
