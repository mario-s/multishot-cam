package de.mario.photo.controller;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.PhotoActivable;
import de.mario.photo.SettingsAccess;
import de.mario.photo.controller.lookup.CameraLookup;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.controller.preview.FocusView;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

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
    @Mock
    private MessageSender messageSender;
    @Mock
    private Handler handler;
    @Mock
    private SettingsAccess settingsAccess;
    @Mock
    private StorageLookable storageLookable;
    @InjectMocks
    private CameraController classUnderTest;

    @Before
    public void setUp() {
        given(cameraLookup.findBackCamera()).willReturn(1);
        given(cameraFactory.getCamera(anyInt())).willReturn(camera);
        given(camera.getParameters()).willReturn(parameters);
        given(activity.getContext()).willReturn(context);
        given(activity.getHandler()).willReturn(handler);
        given(activity.getSettingsAccess()).willReturn(settingsAccess);

        classUnderTest.setActivity(activity);
        classUnderTest.setStorageLookup(storageLookable);

        setInternalState(classUnderTest, "focusView", focusView);
        setInternalState(classUnderTest, "messageSender", messageSender);
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

    @Test
    public void testShot_NoDirectory() {
        classUnderTest.shot();
        verify(messageSender).toast(anyString());
    }
}
