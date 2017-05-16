package de.mario.photo;

import android.widget.ImageButton;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.ViewsMediatable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = PhotoApp.class)
public class PhotoActivityTest {

    private PhotoActivity classUnderTest;

    private CameraControlable cameraController;

    private ViewsMediatable viewsMediator;

    @Before
    public void setUp(){
        ActivityController<PhotoActivity> controller = Robolectric.buildActivity(PhotoActivity.class);
        classUnderTest = controller.attach().create().get();

        cameraController = mock(CameraControlable.class);
        viewsMediator = mock(ViewsMediatable.class);

        setInternalState(classUnderTest, "cameraController", cameraController);
        setInternalState(classUnderTest, "viewsMediator", viewsMediator);

        ImageView imageButton = mock(ImageView.class);
        setInternalState(classUnderTest, "imageButton", imageButton);
    }

    @Test
    public void testOnResume() {
        classUnderTest.onResume();
        verify(cameraController).reinitialize();
        verify(viewsMediator).setupViews();
    }

    @Test
    public void testShutter() {
        ImageButton btn = (ImageButton) classUnderTest.findViewById(R.id.shutter_button);
        btn.performClick();
        verify(cameraController).shot();
    }


}
