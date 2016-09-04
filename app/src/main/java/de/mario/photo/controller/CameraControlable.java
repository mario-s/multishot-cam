package de.mario.photo.controller;

import android.hardware.Camera;
import android.os.Message;

import java.io.File;

import de.mario.photo.PhotoActivable;
import de.mario.photo.settings.SettingsAccess;
import de.mario.photo.view.FocusView;
import de.mario.photo.view.Preview;

/**
 */
public interface CameraControlable {

    /**
     * Look for a camera and return true if we got one.
     * @return boolean
     */
    boolean lookupCamera();

    /**
     * Initialize the camera. Make sure that you called lookup before and got a true value.
     */
    void initialize();

    void reinitialize();

    void releaseCamera();

    void shot();

    Preview getPreview();

    FocusView getFocusView();

    String[] getIsoValues();

    String getSelectedIsoValue(String isoKey);

    String[] getSupportedPicturesSizes();

    String getSelectedPictureSize();

    String findIsoKey();

    void setActivity(PhotoActivable activity);

    Camera getCamera();

    File getPictureSaveDirectory();

    void send(Message message);

    SettingsAccess getSettingsAccess();
}
