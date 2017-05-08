package de.mario.photo.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import de.mario.photo.R;
import de.mario.photo.support.BitmapLoader;
import de.mario.photo.glue.BitmapLoadable;

/**
 */
public class ImageToast extends Toast{

    private final BitmapLoadable loader;
    private ImageView imageView;
    private TextView textView;
    private View toastView;

    public ImageToast(View toastView) {
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

    public ImageToast setImage(File file) {
        imageView.setImageBitmap(loader.loadThumbnail(file));
        return this;
    }

    public ImageToast setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ImageToast setText(String text) {
        textView.setText(text);
        return this;
    }
}
