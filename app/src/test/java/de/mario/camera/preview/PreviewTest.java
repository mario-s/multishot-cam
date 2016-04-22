package de.mario.camera.preview;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
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

    @Mock
    private Resources resources;

    @Mock
    private Configuration configuration;

    @Mock
    private Display display;

    @Mock
    private RelativeLayout.LayoutParams layoutParams;

    private Preview classUnderTest;

    @Before
    public void setUp() {

        Size size = camera.new Size(720, 1280);
        List<Size> sizes = singletonList(size);

        given(camera.getParameters()).willReturn(cameraParameters);
        given(resources.getConfiguration()).willReturn(configuration);
        given(cameraParameters.getSupportedPreviewSizes()).willReturn(sizes);
        given(cameraParameters.getSupportedPictureSizes()).willReturn(sizes);

        classUnderTest = new Preview(activity, camera) {
            @Override
            public SurfaceHolder getHolder() {
                return surfaceHolder;
            }

            @Override
            public Resources getResources() {
                return resources;
            }

            @Override
            Display getDefaultDisplay() {
                return display;
            }

            @Override
            public ViewGroup.LayoutParams getLayoutParams() {
                return layoutParams;
            }
        };

        List<Dim> dims = singletonList(new Dim(720,1280));
        classUnderTest.setPictureSizeList(dims);
        classUnderTest.setPreviewSizeList(dims);
    }

    @Test
    public void testSurfaceCreated() throws IOException{
        classUnderTest.surfaceCreated(surfaceHolder);
        InOrder inOrder =  inOrder(camera, camera);
        inOrder.verify(camera).setPreviewDisplay(surfaceHolder);
        inOrder.verify(camera).startPreview();
    }

    @Test
    public void testSurfaceChanged() {
        classUnderTest.surfaceChanged(surfaceHolder, 0, 720, 1280);

        InOrder inOrder =  inOrder(camera, camera);
        inOrder.verify(camera).stopPreview();
        inOrder.verify(camera).startPreview();
    }
}
