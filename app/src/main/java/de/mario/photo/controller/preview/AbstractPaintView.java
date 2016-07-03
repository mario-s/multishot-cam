package de.mario.photo.controller.preview;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

/**
 * This class returns a {@link Paint} by using the {@link PaintFactory}.
 */
abstract class AbstractPaintView extends View{

    private PaintFactory paintFactory;

    private Paint paint;

    public AbstractPaintView(Context context){
        super(context);
        paintFactory = new PaintFactory();
    }

    /**
     * Either returns a new Paint of an existing if this method was called before.
     * @return {@link Paint}
     */
    protected Paint getPaint() {
        if(paint == null){
            paint = paintFactory.create();
        }
        return paint;
    }
}
