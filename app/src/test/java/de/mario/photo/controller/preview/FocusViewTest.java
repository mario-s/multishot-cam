package de.mario.photo.controller.preview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class FocusViewTest {
    @Mock
    private Context context;
    @Mock
    private Paint paint;
    @Mock
    private PaintFactory paintFactory;
    @InjectMocks
    private FocusView classUnderTest;

    @Before
    public void setUp() {
        given(paintFactory.create()).willReturn(paint);
        setInternalState(classUnderTest, "paintFactory", paintFactory);
    }

    @Test
    public void testFocused_Success() {
        classUnderTest.focused(true);
        verify(paint).setColor(Color.GREEN);
    }

    @Test
    public void testFocused_Failed() {
        classUnderTest.focused(false);
        verify(paint).setColor(Color.RED);
    }
}
