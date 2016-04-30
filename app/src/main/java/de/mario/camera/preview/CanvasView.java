package de.mario.camera.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * This view is on top of the preview and provides painting capabilities for a better feedback.
 */
public class CanvasView extends View {

    private static final int WIDTH = 1;
    private static final int RADIUS = 50;

    private Paint focusPaint;
    private Paint gridPaint;

    private boolean showGrid;

    public CanvasView(Context context) {
        super(context);
        focusPaint = createPaint();
        gridPaint = createPaint();
    }

    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, focusPaint);
        if(showGrid) {
            //do grid
        }
    }

    public void showGrid(boolean show) {
        boolean oldVal = showGrid;
        if(oldVal != show) {
            showGrid = show;
            postInvalidate();
        }
    }

    /**
     * changes the indicator to show successful or failed focus
     */
    public void focused(boolean success) {
        if(success) {
            repaintFocusIndicator(Color.GREEN);
        }else{
            repaintFocusIndicator(Color.RED);
        }
    }

    /**
     * resets the indicator to default
     */
    public void resetFocus() {
        repaintFocusIndicator(Color.WHITE);
    }

    private void repaintFocusIndicator(int color) {
        focusPaint.setColor(color);
        postInvalidate();
    }


}
