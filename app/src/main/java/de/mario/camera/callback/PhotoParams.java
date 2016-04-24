package de.mario.camera.callback;

import android.os.Handler;
import android.view.View;

import java.io.File;
import java.util.Map.Entry;

import de.mario.camera.PhotoActivable;

/**
 */
final class PhotoParams {

    private Entry<String, Integer>[] photosWithEv;

    private final PhotoActivable activity;


    PhotoParams(PhotoActivable activity) {
        this.photosWithEv = photosWithEv;
        this.activity = activity;
    }

    Entry<String, Integer>[] getExposureEntries() {
        return photosWithEv;
    }

    void setPhotosWithEv(Entry<String, Integer>[] photosWithEv) {
        this.photosWithEv = photosWithEv;
    }

    /**
     * Return the image path on external storage.
     * @return path as string
     */
    File getPictureFileDir() {
        return activity.getPicturesDirectory();
    }

    File getInternalDirectory() {
        return activity.getInternalDirectory();
    }

    Handler getHandler(){
        return activity.getHandler();
    }

    String getResource(int key) {
        return activity.getResource(key);
    }

    View getPreview() {
        return activity.getPreview();
    }
}
