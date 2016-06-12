package de.mario.camera;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewsOrientationListenerTest {
    @Mock
    private Context context;
    @Mock
    private View view;
    @InjectMocks
    private ViewsOrientationListener classUnderTest;

    @Before
    public void setUp() {
        classUnderTest.addView(view);
    }

    @Test
    public void testOnOrientationChanged() {
        classUnderTest.onOrientationChanged(1);
        verify(view).setRotation(359);
    }
}
