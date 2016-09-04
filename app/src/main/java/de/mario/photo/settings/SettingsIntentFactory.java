package de.mario.photo.settings;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.support.IsoSupport;
import de.mario.photo.support.PicturesSizeSupport;


/**
 * This factory creates an intent to launch the setting activity
 */
public final class SettingsIntentFactory {

    static final String PICTURE_SIZES = "pictureSizes";
    static final String SELECTED_PICTURE_SIZE = "selectedPictureSize";
    static final String SELECTED_ISO = "selectedIso";
    static final String ISOS = "isos";

    private IsoSupport isoSupport;
    private PicturesSizeSupport sizeSupport;

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

        prepareSupport();

        addPictureSizes(intent);
        addIsos(intent);

        return intent;
    }

    private void prepareSupport() {
        if (isoSupport == null) {
            isoSupport = new IsoSupport(cameraController.getCamera().getParameters());
        }
        if (sizeSupport == null) {
            sizeSupport = new PicturesSizeSupport(cameraController.getCamera().getParameters());
        }
    }

    private void addIsos(Intent intent) {
        String isoKey = findIsoKey();
        if (!isoKey.isEmpty()) {
            intent.putExtra(SELECTED_ISO, isoSupport.getSelectedIsoValue(isoKey));
            intent.putExtra(ISOS, isoSupport.getIsoValues());
        }
    }

    private void addPictureSizes(Intent intent) {
        intent.putExtra(PICTURE_SIZES, sizeSupport.getSupportedPicturesSizes());
        intent.putExtra(SELECTED_PICTURE_SIZE, sizeSupport.getSelectedPictureSize(cameraController.getCamera()));
    }

    private String findIsoKey() {
        //exists the ISO key in the settings?
        String isoKey = settingsAccess.getIsoKey();
        if(isoKey.isEmpty()) {
            //if not look for it
            isoKey = isoSupport.findIsoKey();
            settingsAccess.setIsoKey(isoKey);
        }
        return isoKey;
    }

}
