package de.mario.photo.glue;

import android.view.ViewGroup;

/**
 *
 */

public interface ViewsMediatable {
    void setPreview(ViewGroup preview);

    void setupViews();

    void removeViews();

    void updatePaintViews();
}
