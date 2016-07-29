package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GalleryOpenerTest {
    @Mock
    private Context context;
    @Mock
    private Uri uri;
    @InjectMocks
    private GalleryOpener classUnderTest;

    @Test
    public void testOpen_UriExists() {
        classUnderTest.open(uri);
        verify(context).startActivity(any(Intent.class));
    }
}
