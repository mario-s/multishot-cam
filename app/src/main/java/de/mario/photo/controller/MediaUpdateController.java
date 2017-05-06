package de.mario.photo.controller;

import android.graphics.Bitmap;

import java.io.File;

import de.mario.photo.glue.BitmapLoadable;
import de.mario.photo.glue.GalleryOpenable;
import de.mario.photo.glue.ImageOpenable;
import de.mario.photo.glue.MediaUpdateControlable;
import de.mario.photo.glue.MediaUpdateable;

/**
 * This class handles the update for the media
 */
public class MediaUpdateController implements MediaUpdateControlable {
    private static final int IMG_BTN_THUMB = 38;

    private MediaUpdateable mediaUpdater;
    private ImageOpenable imageOpener;
    private GalleryOpenable galleryOpener;
    private BitmapLoadable bitmapLoader;

    void initialize() {
        bitmapLoader.setThumbnailSize(IMG_BTN_THUMB);
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

    void setMediaUpdater(MediaUpdateable mediaUpdater) {
        this.mediaUpdater = mediaUpdater;
    }

    void setImageOpener(ImageOpenable imageOpener) {
        this.imageOpener = imageOpener;
    }

    void setGalleryOpener(GalleryOpenable galleryOpener) {
        this.galleryOpener = galleryOpener;
    }

    void setBitmapLoader(BitmapLoadable bitmapLoader) {
        this.bitmapLoader = bitmapLoader;
    }
}
