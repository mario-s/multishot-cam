package de.mario.photo.exif;

import android.media.ExifInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ExifWriterTest {
    @Mock
    private ExifInterface exifInterface;

    private File src;

    private Map<String, String> map;

    private ExifWriter classUnderTest;

    @Before
    public void setUp() {
        src = new File(".");
        map = new HashMap<>();
        map.put(ExifTag.MAKE, "test");

        classUnderTest = new ExifWriter(){
            @Override
            ExifInterface getExifInterface(File file) throws IOException {
                return exifInterface;
            }
        };
    }

    @Test
    public void testAddTags() throws IOException {

        classUnderTest.addTags(src, map);
        verify(exifInterface).saveAttributes();
    }
}
