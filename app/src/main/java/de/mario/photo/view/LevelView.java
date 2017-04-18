package de.mario.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.OrientationEventListener;

import javax.inject.Inject;

/**
 * This view draws an indicator when the device is near horizontal or vertical.
 */
public class LevelView extends AbstractPaintView {

    private static final int TOLERANCE = 1;

    private final OrientationNoiseFilter noiseFilter;

    private final OrientationEventListener listener;

    private int orientation;

    private boolean showLevel;

    @Inject
    public LevelView(Context context) {
        super(context);
        this.noiseFilter = new OrientationNoiseFilter();
        this.listener = new LevelOrientationListener(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLevel(canvas);
    }

    void drawLevel(Canvas canvas) {
        if (showLevel) {
            Paint paint = getPaint();
            if (isHorizontal(orientation) || isVertical(orientation)) {
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.WHITE);
            }

            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int cx = width / 2;
            int cy = height / 2;

            float angle = -orientation;
            canvas.rotate(angle, cx, cy);
            canvas.drawLine(cx - (cx * 2 / 3), cy, cx + (cx * 2 / 3), cy, paint);
        }
    }

    private boolean isHorizontal(int orientation) {
        return Math.abs(90 - orientation) <= TOLERANCE || Math.abs(270 - orientation) <= TOLERANCE;
    }

    private boolean isVertical(int orientation) {
        return Math.abs(0 - orientation) <= TOLERANCE || Math.abs(180 - orientation) <= TOLERANCE;
    }

    public void enable(boolean enabled) {
        this.showLevel = enabled;
        if (showLevel) {
            listener.enable();
        } else {
            listener.disable();
        }
    }

    private class LevelOrientationListener extends OrientationEventListener {

        LevelOrientationListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int arg) {
            if (arg != OrientationEventListener.ORIENTATION_UNKNOWN) {
                orientation = noiseFilter.filter(orientation, arg);
                invalidate();
            }
        }
    }
}
