package de.mario.camera.callback;

import android.os.Handler;
import android.view.View;

import java.io.File;

import de.mario.camera.PhotoActivable;

/**
 */
final class PhotoParams {

    private Shot[] photosWithEv;

    private final PhotoActivable activity;


    PhotoParams(PhotoActivable activity) {
        this.photosWithEv = photosWithEv;
        this.activity = activity;
    }

    Shot[] getExposureEntries() {
        return photosWithEv;
    }

    void setPhotosWithEv(Shot[] photosWithEv) {
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
