package de.mario.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.lang.System;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ContinuesCallbackTest {

    public static final String TEST = "test";
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

    private ContinuesCallback classUnderTest;

    private byte [] testData;

    @Before
    public void setUp() {
        Queue<Integer> exVals = new LinkedList<>();
        exVals.add(0);
        exVals.add(-1);
        exVals.add(1);
        when(activity.getExposureValues()).thenReturn(exVals);

        when(activity.getHandler()).thenReturn(handler);
        when(activity.getResource(anyInt())).thenReturn(TEST);

        when(camera.getParameters()).thenReturn(params);

        testData = TEST.getBytes();
    }

    @Test
    public void testOnPictureTaken() {
        String folder = getClass().getResource(".").getFile();
        when(activity.getPicturesDirectory()).thenReturn(new File(folder));
        classUnderTest = new ContinuesCallback(activity){
            @Override
            Message createMessage(String msg) {
                return message;
            }
        };
        classUnderTest.onPictureTaken(testData, camera);
        verify(camera).setParameters(params);
    }

    @Test
    public void testOnPictureTaken_MissingPictureFile() {
        when(activity.getPicturesDirectory()).thenReturn(new File("foo.bar"));
        classUnderTest = new ContinuesCallback(activity){
            @Override
            Message createMessage(String msg) {
                return message;
            }
        };
        classUnderTest.onPictureTaken(testData, camera);
        verify(handler).sendMessage(any(Message.class));
    }

}
