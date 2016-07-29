package de.mario.photo.controller.shot;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ParameterUpdaterTest {
    @Mock
    private Camera camera;

    @Mock
    private Camera.Parameters parameters;

    private ParameterUpdater classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ParameterUpdater(parameters);
    }

    @Test
    public void testSetPictureSize() {
        Camera.Size size = camera.new Size(0, 0);
        given(parameters.getSupportedPictureSizes()).willReturn(singletonList(size));
        classUnderTest.setPictureSize("0x0");
        verify(parameters).setPictureSize(0, 0);
    }

    @Test
    public void testReset() {
        classUnderTest.reset();
        verify(parameters).setExposureCompensation(0);
        verify(parameters).setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
    }

    @Test
    public void testEnableContinuesFlash() {
        classUnderTest.enableContinuesFlash(true);
        verify(parameters).setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
    }
}
