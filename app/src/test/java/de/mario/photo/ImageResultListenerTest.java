package de.mario.photo;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.glue.PhotoActivable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageResultListenerTest {
    @Mock
    private Context context;
    @Mock
    private Intent intent;
    @Mock
    private PhotoActivity activity;
    @InjectMocks
    private ImageResultListener classUnderTest;

    @Before
    public void setUp() {
        given(intent.getAction()).willReturn(PhotoActivable.EXPOSURE_MERGE);
    }

    @Test
    public void testOnReceive() {
        classUnderTest.onReceive(context, intent);
        InOrder order = inOrder(intent, activity);
        order.verify(intent).getStringExtra(PhotoActivable.MERGED);
        order.verify(activity).refreshPictureFolder(anyString());
    }

    @Test
    public void testHandleMessage() {
        classUnderTest.handleMessage(null);
        verify(activity).toggleImageButton();
    }
}
