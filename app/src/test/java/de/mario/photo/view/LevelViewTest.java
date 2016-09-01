package de.mario.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelViewTest {
    @Mock
    private Context context;
    @Mock
    private Paint paint;
    @Mock
    private Canvas canvas;
    @Mock
    private PaintFactory paintFactory;

    private LevelView classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new LevelView(context) {
            @Override
            boolean isShowLevel() {
                return true;
            }
        };

        given(paintFactory.create()).willReturn(paint);
        setInternalState(classUnderTest, "paintFactory", paintFactory);
    }

    @Test
    public void testVertical_DrawLevelGreen() {
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testHorizontal_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 90);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testNotLeveled_DrawLevelWhite() {
        setInternalState(classUnderTest, "orientation", 45);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.WHITE);
    }

    @Test
    public void testOneBelow90_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 89);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneAbove90_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 91);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneBelow270_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 269);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneAbove270_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 271);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneBelow0_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", -1);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneAbove0_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 1);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneBelow180_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 179);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testOneAbove180_DrawLevelGreen() {
        setInternalState(classUnderTest, "orientation", 181);
        classUnderTest.drawLevel(canvas);
        verify(paint).setColor(Color.GREEN);
    }
}
