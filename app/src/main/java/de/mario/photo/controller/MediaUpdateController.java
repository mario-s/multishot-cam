package de.mario.photo.controller;

import android.graphics.Bitmap;

import com.google.inject.Inject;

import java.io.File;

import de.mario.photo.support.BitmapLoader;
import de.mario.photo.support.GalleryOpener;
import de.mario.photo.support.ImageOpener;
import de.mario.photo.support.MediaUpdater;

/**
 * This class handles the update for the media
 */
public class MediaUpdateController {
    private static final int IMG_BTN_THUMB = 38;

    @Inject
    private MediaUpdater mediaUpdater;
    @Inject
    private ImageOpener imageOpener;
    @Inject
    private GalleryOpener galleryOpener;
    @Inject
    private BitmapLoader bitmapLoader;

    public void initialize() {
        bitmapLoader.setThumbnailSize(IMG_BTN_THUMB);
    }

    public void openGallery() {
        galleryOpener.open();
    }

    public void openImage() {
        File last = mediaUpdater.getLastUpdated();

        if (last != null) {
            imageOpener.open(last);
        }
    }

    public Bitmap getLastUpdated() {
        Bitmap lastBitmap = null;
        File lastFile = mediaUpdater.getLastUpdated();
        if (lastFile != null) {
            lastBitmap = bitmapLoader.loadThumbnail(lastFile);
        }
        return lastBitmap;
    }

    public void sendUpdate(File file) {
        mediaUpdater.sendUpdate(file);
    }
}
