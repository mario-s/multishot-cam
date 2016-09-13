package de.mario.photo;

import android.widget.ImageButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import de.mario.photo.controller.CameraApi1Controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class PhotoActivityTest {

    private PhotoActivity classUnderTest;

    private CameraApi1Controller cameraController;

    @Before
    public void setUp(){
        ActivityController<PhotoActivity> controller = Robolectric.buildActivity(PhotoActivity.class);
        classUnderTest = controller.attach().create().get();
        cameraController = mock(CameraApi1Controller.class);
        setInternalState(classUnderTest, "cameraController", cameraController);
    }

    @Test
    public void testShutter() {
        ImageButton btn = (ImageButton) classUnderTest.findViewById(R.id.shutter_button);
        btn.performClick();
        verify(cameraController).shot();
    }
}
