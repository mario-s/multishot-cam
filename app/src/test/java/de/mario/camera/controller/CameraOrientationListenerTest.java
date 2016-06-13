package de.mario.camera.controller;

import android.content.Context;
import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class CameraOrientationListenerTest {
    @Mock
    private Context context;
    @Mock
    private Camera camera;
    @Mock
    private Camera.Parameters parameters;
    @InjectMocks
    private CameraOrientationListener classUnderTest;

    @Before
    public void setUp() {
        given(camera.getParameters()).willReturn(parameters);
        classUnderTest.setCamera(camera);
    }

    @Test
    public void testOnOrientationChanged() {
        classUnderTest.onOrientationChanged(1);
        verify(parameters).setRotation(90);
    }
}
