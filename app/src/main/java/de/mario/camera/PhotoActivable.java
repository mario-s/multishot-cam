package de.mario.camera;

import android.content.Context;
import android.os.Handler;

import java.io.File;

import de.mario.camera.controller.preview.Preview;

/**
 * Interface for coupling between the activity and sub classes.
 */
public interface PhotoActivable {

    String PIC_SIZE_KEY = "%sx%s";

    String DEBUG_TAG = "PhotoActivity";
    String PICTURES = "pictures";

    String EXPOSURE_MERGE = "exposureMerge";

    String getResource(int key);

    Handler getHandler();

    /**
     * Directory where to save the images on a mounted storage.
     * @return the directory
     */
    File getPicturesDirectory();

    Preview getPreview();

    SettingsAccess getSettingsAccess();

    void prepareForNextShot();

    Context getContext();

}
