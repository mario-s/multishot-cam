package de.mario.photo.view;

import android.content.Context;

import de.mario.photo.settings.SettingsAccess;

/**
 */
abstract class AbstractSettingsAccessPaintView extends AbstractPaintView {

    protected final SettingsAccess settingsAccess;

    public AbstractSettingsAccessPaintView(Context context) {
        super(context);
        this.settingsAccess = new SettingsAccess(context);
    }
}
