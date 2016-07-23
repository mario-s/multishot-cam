package de.mario.photo;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 */
class ImageToast extends Toast{

    private ImageView imageView;

    private TextView textView;

    private View toastView;

    ImageToast(View toastView) {
        super(toastView.getContext());
        this.toastView = toastView;
        init();
    }

    private void init() {
        setDuration(Toast.LENGTH_LONG);

        imageView = (ImageView) toastView.findViewById(R.id.toast_image);
        textView = (TextView) toastView.findViewById(R.id.toast_text);

        imageView.setImageResource(R.drawable.ic_launcher);

        setView(toastView);
    }

    ImageToast setImage(String path) {
        File file = new File(path);
        imageView.setImageURI(Uri.fromFile(file));
        return this;
    }

    ImageToast setText(String text) {
        textView.setText(text);
        return this;
    }
}
