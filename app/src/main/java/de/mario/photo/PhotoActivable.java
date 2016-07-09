package de.mario.photo;

import android.content.Context;
import android.os.Handler;

import de.mario.photo.settings.SettingsAccess;

/**
 * Interface for coupling between the activity and sub classes.
 */
public interface PhotoActivable {

    String PIC_SIZE_KEY = "%sx%s";

    String DEBUG_TAG = "PhotoActivity";
    String PICTURES = "pictures";
    String SAVE_FOLDER = "saveFolder";

    String EXPOSURE_MERGE = "exposureMerge";
    String MERGED = "merged";

    Handler getHandler();

    SettingsAccess getSettingsAccess();

    void prepareForNextShot();

    Context getContext();
}
