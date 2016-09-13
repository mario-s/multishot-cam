package de.mario.photo.controller.shot;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.controller.CameraApi1Controller;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.settings.SettingsAccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoCommandTest {

    private PhotoCommand classUnderTest;

    @Mock
    private CameraApi1Controller cameraController;
    @Mock
    private PhotoActivable photoActivable;
    @Mock
    private Camera camera;
    @Mock
    private SettingsAccess settings;
    @Mock
    private PhotoShotsFactory photoShotsFactory;
    @Mock
    private ParameterUpdater parameterUpdater;
    @Mock
    private PictureCallback pictureCallback;

    @Before
    public void setUp() {
        given(cameraController.getSettingsAccess()).willReturn(settings);
        given(settings.getPicSizeKey()).willReturn("");
        given(photoShotsFactory.create(settings)).willReturn(new Shot[]{new Shot("test")});

        classUnderTest = new PhotoCommand(camera, cameraController, photoActivable) {
            @Override
            PictureCallback newPictureCallback() {
                return pictureCallback;
            }
        };
        setInternalState(classUnderTest, "photoShotsFactory", photoShotsFactory);
        setInternalState(classUnderTest, "parameterUpdater", parameterUpdater);
    }

    @Test
    public void testRun() {
        classUnderTest.run();
        InOrder inOrder =  inOrder(parameterUpdater, camera);
        inOrder.verify(parameterUpdater).update(camera);
        inOrder.verify(camera).takePicture(any(ShutterCallback.class), any(PictureCallback.class), eq(pictureCallback));
    }
}
