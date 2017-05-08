package de.mario.photo.controller.media;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import de.mario.photo.controller.media.MediaUpdateController;
import de.mario.photo.support.BitmapLoader;
import de.mario.photo.controller.media.GalleryOpener;
import de.mario.photo.controller.media.ImageOpener;
import de.mario.photo.controller.media.MediaUpdater;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaUpdateControllerTest {
    @Mock
    private Context context;
    @InjectMocks
    private MediaUpdater mediaUpdater;
    @Mock
    private ImageOpener imageOpener;
    @Mock
    private GalleryOpener galleryOpener;
    @Mock
    private BitmapLoader bitmapLoader;
    @InjectMocks
    private MediaUpdateController classUnderTest;
    @Mock
    private File file;
    @Mock
    private Bitmap bitmap;
    @Mock
    private Intent intent;

    @Before
    public void setUp() {
        given(context.getString(anyInt())).willReturn("Foo");
        given(galleryOpener.newIntent()).willReturn(intent);
        given(imageOpener.newIntent()).willReturn(intent);
        given(file.exists()).willReturn(true);

        setInternalState(classUnderTest, "mediaUpdater", mediaUpdater);
        setInternalState(classUnderTest, "imageOpener", imageOpener);
        setInternalState(classUnderTest, "galleryOpener", galleryOpener);
        classUnderTest.sendUpdate(file);
    }

    @Test
    public void testOpenGallery() {
        classUnderTest.openGallery();
        verify(galleryOpener).open();
    }

    @Test
    public void testOpenImage() {
        classUnderTest.openImage();
        verify(imageOpener).open(file);
    }

    @Test
    public void testGetLastUpdated_ShouldReturnBitmap() {
        given(bitmapLoader.loadThumbnail(file)).willReturn(bitmap);
        Bitmap result = classUnderTest.getLastUpdated();
        assertThat(result, equalTo(bitmap));
    }
}
