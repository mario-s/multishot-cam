package de.mario.camera.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * View for the focus indicator
 */
public class FocusView extends AbstractCanvasView {

    private static final int RADIUS = 50;

    private Paint focusPaint;

    public FocusView(Context context) {
        super(context);
        focusPaint = createPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, focusPaint);
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
