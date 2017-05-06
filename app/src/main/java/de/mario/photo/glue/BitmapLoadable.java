package de.mario.photo.glue;

import android.graphics.Bitmap;

import java.io.File;

/**
 *
 */
public interface BitmapLoadable {
    void setThumbnailSize(int thumbnailSize);

    Bitmap loadThumbnail(File file);
}
