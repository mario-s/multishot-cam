package de.mario.photo.controller.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import de.mario.photo.SettingsValue;


/**
 * This view is on top of the preview and provides painting capabilities for a better feedback.
 */
public class CanvasView extends AbstractPaintView {

    @Inject
    public CanvasView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint gridPaint = getPaint();

        if (isShowGrid()) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            canvas.drawLine(width / 3.0f, 0.0f, width / 3.0f, height - 1.0f, gridPaint);
            canvas.drawLine(2.0f * width / 3.0f, 0.0f, 2.0f * width / 3.0f, height - 1.0f, gridPaint);
            canvas.drawLine(0.0f, height / 3.0f, width - 1.0f, height / 3.0f, gridPaint);
            canvas.drawLine(0.0f, 2.0f * height / 3.0f, width - 1.0f, 2.0f * height / 3.0f, gridPaint);
        }
    }

    private boolean isShowGrid() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SettingsValue.GRID.getValue(), false);
    }

}
