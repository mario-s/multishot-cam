package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.inject.Inject;

import java.io.File;

import roboguice.util.Ln;


/**
 * Opens the gallery of taken photos.
 */
public class ImageOpener extends AbstractOpener {
    public static final String TYPE = "image/*";

    @Inject
    public ImageOpener(Context context) {
        super(context);
    }

    public void open(File file) {
        if (file != null) {
            Intent intent = resolve(file);
            tryOpen(intent);
        }
    }

    private Intent resolve(File file) {
        Intent intent = newIntent();

        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, TYPE);
        Ln.d("set intent to show image");

        return intent;
    }

}
