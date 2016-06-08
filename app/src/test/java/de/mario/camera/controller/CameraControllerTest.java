package de.mario.camera.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.camera.PhotoActivable;
import de.mario.camera.controller.lookup.CameraLookup;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class CameraControllerTest {

    @Mock
    private PhotoActivable activity;

    @Mock
    private CameraLookup cameraLookup;

    @Mock
    private CameraFactory cameraFactory;

    @InjectMocks
    private CameraController classUnderTest;

    @Test
    public void testLookupCamera_True() {
        given(cameraLookup.findBackCamera()).willReturn(1);
        boolean result = classUnderTest.lookupCamera();
        assertTrue(result);
    }

    @Test
    public void testLookupCamera_False() {
        given(cameraLookup.findBackCamera()).willReturn(-1);
        boolean result = classUnderTest.lookupCamera();
        assertFalse(result);
    }
}
