package de.mario.camera.controller;

import android.content.Context;
import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.camera.PhotoActivable;
import de.mario.camera.controller.lookup.CameraLookup;
import de.mario.camera.controller.preview.FocusView;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyInt;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class CameraControllerTest {

    @Mock
    private PhotoActivable activity;
    @Mock
    private Context context;
    @Mock
    private CameraLookup cameraLookup;
    @Mock
    private CameraFactory cameraFactory;
    @Mock
    private Camera camera;
    @Mock
    private FocusView focusView;
    @Mock
    private Camera.Parameters parameters;
    @InjectMocks
    private CameraController classUnderTest;

    @Before
    public void setUp() {
        classUnderTest.setFocusView(focusView);

        given(cameraLookup.findBackCamera()).willReturn(1);
        given(cameraFactory.getCamera(anyInt())).willReturn(camera);
        given(camera.getParameters()).willReturn(parameters);
        given(activity.getContext()).willReturn(context);
    }

    @Test
    public void testLookupCamera_True() {
        boolean result = classUnderTest.lookupCamera();
        assertTrue(result);
    }

    @Test
    public void testLookupCamera_False() {
        given(cameraLookup.findBackCamera()).willReturn(-1);
        boolean result = classUnderTest.lookupCamera();
        assertFalse(result);
    }

    @Test
    public void testInitialize() {
        classUnderTest.initialize();
        assertNotNull(classUnderTest.getPreview());
    }
}
