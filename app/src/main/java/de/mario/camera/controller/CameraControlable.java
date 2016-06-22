package de.mario.camera.controller;

import de.mario.camera.controller.preview.FocusView;
import de.mario.camera.controller.preview.Preview;

/**
 */
public interface CameraControlable {

    boolean lookupCamera();

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
}
