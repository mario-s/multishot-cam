package de.mario.photo.glue;

import android.graphics.Bitmap;

import java.io.File;

/**
 *
 */

public interface MediaUpdateControlable {
    void openGallery();

    void openImage();

    Bitmap getLastUpdated();

    void sendUpdate(File file);
}
