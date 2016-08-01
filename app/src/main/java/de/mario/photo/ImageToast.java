package de.mario.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 */
class ImageToast extends Toast{

    private static final int THUMBSIZE = 96;
    private final Matrix matrix;
    private ImageView imageView;
    private TextView textView;
    private View toastView;

    ImageToast(View toastView) {
        super(toastView.getContext());
        this.toastView = toastView;
        this.matrix = new Matrix();
        init();
    }

    private void init() {
        matrix.postRotate(90);

        setDuration(Toast.LENGTH_LONG);

        imageView = (ImageView) toastView.findViewById(R.id.toast_image);
        textView = (TextView) toastView.findViewById(R.id.toast_text);

        imageView.setImageResource(R.drawable.ic_launcher);

        setView(toastView);
    }

    private Bitmap getThumbnail(File file) {
        Bitmap source = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeFile(file.getAbsolutePath()),
                THUMBSIZE,
                THUMBSIZE);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    ImageToast setImage(File file) {
        imageView.setImageBitmap(getThumbnail(file));
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
