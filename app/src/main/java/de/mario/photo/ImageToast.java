package de.mario.photo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 */
class ImageToast extends Toast{

    private ImageView imageView;

    private TextView textView;

    private View toastView;

    ImageToast(Context context, View toastView){
        super(context);
        this.toastView = toastView;
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        setDuration(Toast.LENGTH_LONG);

        imageView = (ImageView) toastView.findViewById(R.id.toast_image);
        textView = (TextView) toastView.findViewById(R.id.toast_text);

        setView(toastView);
    }

    ImageToast setImageResource(){
        imageView.setImageResource(R.drawable.ic_launcher);
        return this;
    }

    ImageToast setText(String text) {
        textView.setText(text);
        return this;
    }
}
