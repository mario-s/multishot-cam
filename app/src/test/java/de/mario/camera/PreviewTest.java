package de.mario.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PreviewTest {

    @Mock
    private Activity activity;

    @Mock
    private Camera camera;

    @Mock
    private SurfaceHolder surfaceHolder;

    @Mock
    private Camera.Parameters cameraParameters;

    private Preview classUnderTest;

    @Before
    public void setUp() {
        given(camera.getParameters()).willReturn(cameraParameters);

        classUnderTest = new Preview(activity, camera) {
            @Override
            public SurfaceHolder getHolder() {
                return surfaceHolder;
            }
        };
    }

    @Test
    public void testSurfaceCreated() throws IOException{
        classUnderTest.surfaceCreated(surfaceHolder);
        InOrder inOrder =  inOrder(camera, camera);
        inOrder.verify(camera).setPreviewDisplay(surfaceHolder);
        inOrder.verify(camera).startPreview();
    }
}
