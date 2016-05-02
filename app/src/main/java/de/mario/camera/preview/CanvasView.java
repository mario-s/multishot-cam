package de.mario.camera.preview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import de.mario.camera.PhotoActivable;
import de.mario.camera.SettingsValue;

import static de.mario.camera.preview.PaintFactory.createPaint;


/**
 * This view is on top of the preview and provides painting capabilities for a better feedback.
 */
public class CanvasView extends View {

    private Paint gridPaint;

    public CanvasView(Context context) {
        super(context);
        gridPaint = createPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
