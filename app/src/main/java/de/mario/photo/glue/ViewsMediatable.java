package de.mario.photo.glue;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Mediator interface to handle the views in one place.
 */
public interface ViewsMediatable {
    void setPreview(ViewGroup preview);

    void setProgressBar(View progressBar);

    void setDisplayImageView(ImageView imageView);

    /**
     * Updates the view (normally a button) which shows the merged image.
     * If the parameter is null the view will not be displayed.
     * @param bitmap a previews of the image from the merge process.
     */
    void updateDisplayImageView(Bitmap bitmap);

    void showProgressBar(boolean show);

    void setupViews();

    void removeViews();

    void updatePaintViews();
}
