package de.mario.photo;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

import de.mario.photo.controller.CameraControlable;
import de.mario.photo.service.ExposureMergeService;
import de.mario.photo.service.OpenCvService;
import de.mario.photo.settings.SettingsAccess;
import de.mario.photo.settings.SettingsActivity;

/**
 *
 */
class IntentFactory {

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
    IntentFactory(Context context){
        this.context = context;
    }

    Intent newSettingsIntent() {
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

    Intent newOpenCvIntent(String [] pictures) {
        Intent intent = new Intent(context, ExposureMergeService.class);
        intent.putExtra(OpenCvService.PARAM_PICS, pictures);
        return intent;
    }
}
