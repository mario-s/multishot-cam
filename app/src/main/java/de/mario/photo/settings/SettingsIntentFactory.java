package de.mario.photo.settings;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

import de.mario.photo.controller.CameraControlable;


/**
 * This factory creates an intent to launch the setting activity
 */
public final class SettingsIntentFactory {

    static final String PICTURE_SIZES = "pictureSizes";
    static final String SELECTED_PICTURE_SIZE = "selectedPictureSize";
    static final String SELECTED_ISO = "selectedIso";
    static final String ISOS = "isos";

    private Context context;
    @Inject
    private CameraControlable cameraController;
    @Inject
    private SettingsAccess settingsAccess;

    @Inject
    public SettingsIntentFactory(Context context) {
        this.context = context;
    }

    public Intent create() {
        Intent intent = new Intent(context, SettingsActivity.class);

        intent.putExtra(PICTURE_SIZES, cameraController.getSupportedPicturesSizes());
        intent.putExtra(SELECTED_PICTURE_SIZE, cameraController.getSelectedPictureSize());

        String isoKey = findIsoKey();
        if(!isoKey.isEmpty()) {
            intent.putExtra(SELECTED_ISO, cameraController.getSelectedIsoValue(isoKey));
            intent.putExtra(ISOS, cameraController.getIsoValues());
        }
        return intent;
    }

    private String findIsoKey() {
        //exists the ISO key in the settings?
        String isoKey = settingsAccess.getIsoKey();
        if(isoKey.isEmpty()) {
            //if not look for it
            isoKey = cameraController.findIsoKey();
            settingsAccess.setIsoKey(isoKey);
        }
        return isoKey;
    }

}
