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

import de.mario.photo.controller.lookup.CameraLookup;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.settings.SettingsAccess;
import de.mario.photo.view.FocusView;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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
    @Mock
    private CameraOrientationListener orientationListener;
    @InjectMocks
    private CameraController classUnderTest;

    @Before
    public void setUp() {
        given(cameraLookup.findBackCamera()).willReturn(1);
        given(cameraFactory.getCamera(anyInt())).willReturn(camera);
        given(camera.getParameters()).willReturn(parameters);
        given(activity.getContext()).willReturn(context);
        given(activity.getMessageHandler()).willReturn(handler);

        classUnderTest.setActivity(activity);
        classUnderTest.setSettingsAccess(settingsAccess);
        classUnderTest.setStorageLookup(storageLookable);

        setInternalState(classUnderTest, "focusView", focusView);
        setInternalState(classUnderTest, "messageSender", messageSender);
        setInternalState(classUnderTest, "handler", handler);
        setInternalState(classUnderTest, "orientationListener", orientationListener);
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
    public void testShot() {
        classUnderTest.shot();
        verify(handler).post(any(CameraController.ShotRunner.class));
    }

    @Test
    public void testReleaseCamera() {
        classUnderTest.reinitialize();
        classUnderTest.releaseCamera();
        verify(camera).release();
    }

    @Test
    public void testFocusCallBack_Success() {
        classUnderTest.reinitialize();
        CameraController.FocusCallBack instance = classUnderTest.new FocusCallBack();
        instance.onAutoFocus(true, camera);
        verify(settingsAccess).getDelay();
    }

    @Test
    public void testFocusCallBack_Fails() {
        classUnderTest.reinitialize();
        CameraController.FocusCallBack instance = classUnderTest.new FocusCallBack();
        instance.onAutoFocus(false, camera);
    }
}
