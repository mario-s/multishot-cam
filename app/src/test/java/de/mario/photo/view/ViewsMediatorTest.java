package de.mario.photo.view;

import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.SettingsAccessable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewsMediatorTest {

    @Mock
    private SettingsAccessable settingsAccess;
    @Mock
    private CameraControlable cameraController;
    @Mock
    private AbstractPaintView gridView;
    @Mock
    private AbstractPaintView levelView;
    @Mock
    private ViewGroup preview;
    @InjectMocks
    private ViewsMediator classUnderTest;

    @Before
    public void setUp() {
        classUnderTest.setPreview(preview);
    }

    @Test
    public void testSetupViews() {
        classUnderTest.setupViews();
        verify(preview, atLeastOnce()).addView(any(View.class), anyInt());
    }

    @Test
    public void testUpdatePaintViews() {
        classUnderTest.updatePaintViews();
        verify(gridView, atLeastOnce()).enable(false);
    }
}
