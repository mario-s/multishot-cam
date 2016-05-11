package de.mario.camera;

import android.os.Handler;

import java.io.File;
import java.util.Queue;

import de.mario.camera.preview.Preview;

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
     * The exposure values used for the photos.
     * @return a queue with exposure values.
     */
    Queue<Integer> getExposureValues();

    /**
     * Directory where to save the images on a mounted storage.
     * @return the directory
     */
    File getPicturesDirectory();

    Preview getPreview();

    SettingsAccess getSettingsAccess();

}
