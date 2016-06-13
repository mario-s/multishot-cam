package de.mario.camera;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Orientation listener to update the rotation of views.
 */
final class ViewsOrientationListener extends AbstractOrientationListener {

    private final List<View> views;

    ViewsOrientationListener(Context context){
        super(context);
        views = new ArrayList<>();
    }

    @Override
    protected void orientationChanged(int orientation) {
        int angl = 360 - orientation;
        for (View view: views) {
            view.setRotation(angl);
        }
    }

    @Override
    public void enable() {
        if (canDetectOrientation()) {
            super.enable();
        }
    }

    void addView(View view){
        views.add(view);
    }

    void removeView(View view){
        views.remove(view);
    }
}
