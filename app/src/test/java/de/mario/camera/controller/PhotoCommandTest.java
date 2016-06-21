package de.mario.camera.controller;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.camera.PhotoActivable;
import de.mario.camera.SettingsAccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoCommandTest {

    private PhotoCommand classUnderTest;

    @Mock
    private CameraController cameraController;
    @Mock
    private PhotoActivable photoActivable;
    @Mock
    private Camera camera;
    @Mock
    private SettingsAccess settings;
    @Mock
    private MessageSender messageSender;

    @Before
    public void setUp() {
        given(cameraController.getCamera()).willReturn(camera);
        given(photoActivable.getSettingsAccess()).willReturn(settings);

        classUnderTest = new PhotoCommand(cameraController, photoActivable){

            @Override
            MessageSender newSender() {
                return messageSender;
            }
        };
    }

    @Test
    public void testRun_NoDirectory() {
        classUnderTest.run();
        verify(messageSender).toast(anyString());
    }
}
