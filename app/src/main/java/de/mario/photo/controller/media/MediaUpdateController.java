package de.mario.photo.controller.media;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import de.mario.photo.glue.BitmapLoadable;
import de.mario.photo.glue.MediaUpdateControlable;

/**
 * This class handles the update for the media
 */
public class MediaUpdateController implements MediaUpdateControlable {
    private static final int IMG_BTN_THUMB = 38;

    private MediaUpdater mediaUpdater;
    private ImageOpener imageOpener;
    private GalleryOpener galleryOpener;

    private BitmapLoadable bitmapLoader;

    public MediaUpdateController(Context context, BitmapLoadable bitmapLoader) {
        mediaUpdater = new MediaUpdater(context);
        imageOpener = new ImageOpener(context);
        galleryOpener = new GalleryOpener(context);

        setBitmapLoader(bitmapLoader);
    }

    @Override
    public void openGallery() {
        galleryOpener.open();
    }

    @Override
    public void openImage() {
        File last = mediaUpdater.getLastUpdated();

        if (last != null) {
            imageOpener.open(last);
        }
    }

    @Override
    public Bitmap getLastUpdated() {
        Bitmap lastBitmap = null;
        File lastFile = mediaUpdater.getLastUpdated();
        if (lastFile != null) {
            lastBitmap = bitmapLoader.loadThumbnail(lastFile);
        }
        return lastBitmap;
    }

    @Override
    public void sendUpdate(File file) {
        mediaUpdater.sendUpdate(file);
    }

    private void setBitmapLoader(BitmapLoadable bitmapLoader) {
        this.bitmapLoader = bitmapLoader;
        this.bitmapLoader.setThumbnailSize(IMG_BTN_THUMB);
    }
}
