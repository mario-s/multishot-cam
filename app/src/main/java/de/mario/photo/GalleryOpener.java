package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.inject.Inject;

import java.io.File;

import roboguice.util.Ln;


/**
 * Opens the gallery of taken photos.
 */
class GalleryOpener {
    public static final String MODE = "r";
    private final Context context;

    @Inject
    GalleryOpener(Context context) {
        this.context = context;
    }

    void open(File file) {
        showGallery(file);
    }

    private void showGallery(File file) {
        Intent intent = resolve(file);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            showText(getText(R.string.no_gallery_app));
        }
    }

    private Intent resolve(File file) {
        Intent intent = newIntent();
        if (file != null) {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "image/*");
            Ln.d("set intent to show image");
        } else {
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Ln.d("set intent to show gallery");
        }

        return intent;
    }

    Intent newIntent() {
        return new Intent(Intent.ACTION_VIEW);
    }

    private String getText(int id) {
        return context.getString(id);
    }

    private void showText(String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
    }
}
