package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;



/**
 */
public class GalleryOpener extends AbstractOpener {

    private static final String TAG = GalleryOpener.class.getSimpleName();

    public GalleryOpener(Context context) {
        super(context);
    }

    public void open() {
        Intent intent = resolve();
        tryOpen(intent);
    }

    private Intent resolve() {
        Intent intent = newIntent();
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "set intent to show gallery");
        return intent;
    }
}
