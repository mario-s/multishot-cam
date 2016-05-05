package de.mario.camera.preview;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Factory to create a {@link Paint} instance.
 */
class PaintFactory {

    private static final int WIDTH = 1;

    private PaintFactory(){}

    static Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }
}
