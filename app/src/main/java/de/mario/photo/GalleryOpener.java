package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.inject.Inject;


/**
 * Opens the gallery of taken photos.
 */
final class GalleryOpener {
    public static final String MODE = "r";
    private final Context context;

    @Inject
    GalleryOpener(Context context) {
        this.context = context;
    }

    void open(Uri uri) {
        if (uri != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } else {
            String txt = context.getString(R.string.no_pic);
            Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
        }
    }
}
