package de.mario.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import javax.inject.Inject;

import de.mario.photo.settings.SettingsAccess.Value;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


/**
 * This view is on top of the preview and provides painting capabilities for a better feedback.
 */
public class GridView extends AbstractPaintView {

    @Inject
    public GridView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
    }

    void drawGrid(Canvas canvas) {
        if (isShowGrid()) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            Paint gridPaint = getPaint();

            canvas.drawLine(width / 3.0f, 0.0f, width / 3.0f, height - 1.0f, gridPaint);
            canvas.drawLine(2.0f * width / 3.0f, 0.0f, 2.0f * width / 3.0f, height - 1.0f, gridPaint);
            canvas.drawLine(0.0f, height / 3.0f, width - 1.0f, height / 3.0f, gridPaint);
            canvas.drawLine(0.0f, 2.0f * height / 3.0f, width - 1.0f, 2.0f * height / 3.0f, gridPaint);
        }
    }

    boolean isShowGrid() {
        return getDefaultSharedPreferences(getContext()).getBoolean(Value.GRID.getValue(), false);
    }

}
