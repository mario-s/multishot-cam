package de.mario.camera;

import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;

import java.io.File;
import java.util.Queue;

/**
 * Interface for coupling between the activity and sub classes.
 */
public interface PhotoActivable {

    String DEBUG_TAG = "PhotoActivity";
    String PICTURES = "pictures";

    @Deprecated
    Context getApplicationContext();

    String getResource(int key);

    Handler getHandler();

    Queue<Integer> getExposureValues();

    /**
     * Directory where to save the images.
     * @return
     */
    File getPicturesDirectory();

    File getInternalDirectory();
}
