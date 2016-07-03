package de.mario.photo.controller.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * View for the focus indicator
 */
public class FocusView extends AbstractPaintView {

    private static final int RADIUS = 50;

    public FocusView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, getPaint());
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
        getPaint().setColor(color);
        postInvalidate();
    }
}
