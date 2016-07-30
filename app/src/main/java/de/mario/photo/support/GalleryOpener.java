package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.google.inject.Inject;

import roboguice.util.Ln;

/**
 */
public class GalleryOpener extends AbstractOpener {

    @Inject
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
        Ln.d("set intent to show gallery");
        return intent;
    }
}
