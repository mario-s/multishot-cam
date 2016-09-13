package de.mario.photo.controller;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PictureSizeSupportTest {
    @Mock
    private Camera camera;

    @Mock
    private Camera.Parameters parameters;

    private PictureSizeSupport classUnderTest;

    private Camera.Size size;

    @Before
    public void setUp() {
        size = camera.new Size(10, 10);
        given(parameters.getSupportedPictureSizes()).willReturn(singletonList(size));
        classUnderTest = new PictureSizeSupport(parameters);
    }

    @Test
    public void testGetSupportedPictureSize() {
        String[] result = classUnderTest.getSupportedPicturesSizes();
        assertThat(result.length, is(1));
    }

    @Test
    public void testGetSelectedPictureSize() {
        given(parameters.getPictureSize()).willReturn(size);
        String result = classUnderTest.getSelectedPictureSize();
        assertThat(result, containsString("x"));
    }
}
