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

    private ContinuesCallback classUnderTest;

    @Before
    public void setUp() {
        Queue<Integer> exVals = new LinkedList<>();
        exVals.add(0);
        exVals.add(-1);
        exVals.add(1);
        when(activity.getExposureValues()).thenReturn(exVals);

        when(activity.getHandler()).thenReturn(handler);
        when(activity.getResource(anyInt())).thenReturn(TEST);
    }

    @Test
    public void testOnPictureTaken_MissingPictureFile() {
        when(activity.getPicturesDirectory()).thenReturn(new File("foo.bar"));

        classUnderTest = new ContinuesCallback(activity){
            @Override
            Message createMessage(String message) {
                return mock(Message.class);
            }
        };

        byte [] testData = TEST.getBytes();
        classUnderTest.onPictureTaken(testData, camera);
        verify(handler).sendMessage(any(Message.class));
    }
}
