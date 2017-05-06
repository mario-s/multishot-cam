package de.mario.photo.controller.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


import java.io.File;


/**
 * Opens the gallery of taken photos.
 */
class ImageOpener extends AbstractOpener {

    private static final String TAG = ImageOpener.class.getSimpleName();

    public static final String TYPE = "image/*";

    ImageOpener(Context context) {
        super(context);
    }

    void open(File file) {
        if (file != null) {
            Intent intent = resolve(file);
            tryOpen(intent);
        }
    }

    private Intent resolve(File file) {
        Intent intent = newIntent();

        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, TYPE);
        Log.d(TAG, "set intent to show image");

        return intent;
    }

}
