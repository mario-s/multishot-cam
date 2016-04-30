package de.mario.camera.preview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 */
abstract class AbstractCanvasView extends View {
    protected static final int WIDTH = 1;

    AbstractCanvasView(Context context) {
        super(context);
    }

    protected final Paint createPaint() {
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
