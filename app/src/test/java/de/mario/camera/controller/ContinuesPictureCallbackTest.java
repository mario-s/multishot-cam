package de.mario.camera.controller;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import de.mario.camera.PhotoActivable;
import de.mario.camera.controller.preview.Preview;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ContinuesPictureCallbackTest {

    public static final String TEST = "test";

    @Mock
    private Context context;
    @Mock
    private PhotoActivable activity;
    @Mock
    private Handler handler;
    @Mock
    private Camera camera;
    @Mock
    private Camera.Parameters params;
    @Mock
    private Message message;
    @Mock
    private Preview preview;
    @Mock
    private CameraController cameraController;

    private ParameterUpdater updater;

    private byte [] testData;

    private File folder;

    private ShotParameters shotParams;

    @Before
    public void setUp() {
        folder = new File(getClass().getResource(".").getFile());


        given(activity.getHandler()).willReturn(handler);
        given(activity.getResource(anyInt())).willReturn(TEST);
        given(cameraController.getPreview()).willReturn(preview);
        given(cameraController.getPictureSaveDirectory()).willReturn(folder);
        given(camera.getParameters()).willReturn(params);

        updater = new ParameterUpdater(params);

        shotParams = new ShotParameters(cameraController, activity, updater){
            @Override
            Context getContext() {
                return context;
            }
        };
        shotParams.setShots(new Shot[]{new Shot("a"), new Shot("b")});

        testData = TEST.getBytes();
    }

    @Test
    public void testOnPictureTaken() {

        ContinuesPictureCallback classUnderTest = new ContinuesPictureCallback(shotParams);

        classUnderTest.onPictureTaken(testData, camera);
        verify(camera).setParameters(params);
    }

    @Test
    @Ignore("fixme")
    public void testOnPictureTaken_MissingPictureFile() {

        ContinuesPictureCallback classUnderTest = new ContinuesPictureCallback(shotParams);

        classUnderTest.onPictureTaken(testData, camera);
        verify(handler).sendMessage(any(Message.class));
    }

}
