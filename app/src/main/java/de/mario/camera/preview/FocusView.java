package de.mario.camera.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * This view gives feedback about the focus of the camera.
 */
public class FocusView extends View {

    private static final int WIDTH = 2;
    private static final int RADIUS = 50;

    private Paint drawPaint;

    public FocusView(Context context) {
        super(context);
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(WIDTH);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, drawPaint);
    }

    /**
     * changes the indicator to show successful or failed focus
     */
    public void focused(boolean success) {
        if(success) {
            repaint(Color.GREEN);
        }else{
            repaint(Color.RED);
        }
    }

    /**
     * resets the indicator to default
     */
    public void reset() {
        repaint(Color.WHITE);
    }

    private void repaint(int color) {
        drawPaint.setColor(color);
        postInvalidate(); //force repaint
    }

}
