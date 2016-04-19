package de.mario.camera;

import android.hardware.Camera;
import android.widget.ImageButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class PhotoActivityTest {

    PhotoActivity classUnderTest;

    @Before
    public void setUp(){
        classUnderTest = Robolectric.setupActivity(PhotoActivity.class);
    }

    @Test
    public void testShutter() {
        Camera mock = mock(Camera.class);
        classUnderTest.setCamera(mock);
        ImageButton btn = (ImageButton)classUnderTest.findViewById(R.id.shutter);
        btn.performClick();
        verify(mock).autoFocus(any(Camera.AutoFocusCallback.class));
    }
}
