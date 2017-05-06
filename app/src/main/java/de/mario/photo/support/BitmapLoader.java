package de.mario.photo.support;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.File;

import de.mario.photo.glue.BitmapLoadable;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.BitmapFactory.decodeFile;
import static android.media.ThumbnailUtils.extractThumbnail;

/**
 */
public class BitmapLoader implements BitmapLoadable {
    private static final int THUMBSIZE = 96;
    private final Matrix matrix;

    private int thumbnailSize;

    public BitmapLoader() {
        this.matrix = new Matrix();
        init();
    }

    private void init() {
        //matrix.postRotate(DEF);
        thumbnailSize = THUMBSIZE;
    }

    /**
     * Sets the size of the bitmap.
     *
     * @param thumbnailSize size as int
     */
    @Override
    public void setThumbnailSize(int thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    /**
     * Sets the rotation of the bitmap
     *
     * @param degree degree in int
     */
    public void setRotation(int degree) {
        matrix.postRotate(degree);
    }

    /**
     * Loads the bitmap from the file and performs scaling and rotation.
     *
     * @param file source file
     * @return the bitmap
     */
    @Override
    public Bitmap loadThumbnail(File file) {
        Bitmap source = extractThumbnail(
                decodeFile(file.getAbsolutePath()),
                thumbnailSize,
                thumbnailSize);

        return createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
