package de.mario.camera.controller;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CameraFactoryTest {
    @Mock
    private Camera camera;

    @Mock
    private Camera.Parameters parameters;

    private CameraFactory classUnderTest;

    @Before
    public void setUp() {
        given(camera.getParameters()).willReturn(parameters);
        classUnderTest = new CameraFactory(){
            @Override
            Camera open(int id) {
                return camera;
            }
        };
    }

    @Test
    public void testGetCamera() {
        Camera cam = classUnderTest.getCamera(1);
        assertNotNull(cam);
        verify(parameters).setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }
}
