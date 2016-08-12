package de.mario.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class CanvasViewTest {
    @Mock
    private Context context;
    @Mock
    private Paint paint;
    @Mock
    private Canvas canvas;
    @Mock
    private PaintFactory paintFactory;

    private CanvasView classUnderTest;

    private boolean showGrid;

    @Before
    public void setUp() {
        classUnderTest = new CanvasView(context) {
            @Override
            boolean isShowGrid() {
                return showGrid;
            }
        };

        given(paintFactory.create()).willReturn(paint);
        setInternalState(classUnderTest, "paintFactory", paintFactory);
    }

    @Test
    public void testDrawGrid() {
        showGrid = true;
        classUnderTest.drawGrid(canvas);
        verify(canvas, times(4)).drawLine(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(paint));
    }
}
