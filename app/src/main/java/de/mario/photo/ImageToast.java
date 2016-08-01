package de.mario.photo;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import de.mario.photo.support.BitmapLoader;

/**
 */
class ImageToast extends Toast{


    private final BitmapLoader loader;
    private ImageView imageView;
    private TextView textView;
    private View toastView;

    ImageToast(View toastView) {
        super(toastView.getContext());
        this.toastView = toastView;
        this.loader = new BitmapLoader();
        init();
    }

    private void init() {
        setDuration(Toast.LENGTH_LONG);

        imageView = (ImageView) toastView.findViewById(R.id.toast_image);
        textView = (TextView) toastView.findViewById(R.id.toast_text);

        imageView.setImageResource(R.drawable.ic_launcher);

        setView(toastView);
    }

    ImageToast setImage(File file) {
        imageView.setImageBitmap(loader.loadThumbnail(file));
        return this;
    }

    ImageToast setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        return this;
    }

    ImageToast setText(String text) {
        textView.setText(text);
        return this;
    }
}
