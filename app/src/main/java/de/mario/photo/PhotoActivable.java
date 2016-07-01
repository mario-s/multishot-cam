package de.mario.photo;

import android.content.Context;
import android.os.Handler;

/**
 * Interface for coupling between the activity and sub classes.
 */
public interface PhotoActivable {

    String PIC_SIZE_KEY = "%sx%s";

    String DEBUG_TAG = "PhotoActivity";
    String PICTURES = "pictures";
    String SAVE_FOLDER = "saveFolder";

    String EXPOSURE_MERGE = "exposureMerge";

    /**
     * Use getContext().getString(int key);
     */
    @Deprecated
    String getResource(int key);

    Handler getHandler();

    SettingsAccess getSettingsAccess();

    void prepareForNextShot();

    Context getContext();

}
