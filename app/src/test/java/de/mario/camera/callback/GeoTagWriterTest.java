package de.mario.camera.callback;

import android.location.Location;
import android.media.ExifInterface;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;


import java.io.File;
import java.io.IOException;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class GeoTagWriterTest {

    @Mock
    private ExifInterface exifInterface;

    @Mock
    private Location location;

    private File file;

    private GeoTagWriter classUnderTest;

    @Before
    public void setUp() {
        file = new File("");

        classUnderTest = new GeoTagWriter(location) {
            @Override
            ExifInterface getExifInterface(File file) throws IOException {

                return exifInterface;
            }
        };
    }

    @Test
    @Ignore
    public void setGeoTag() throws IOException {
        classUnderTest.setTag(file);

        verify(exifInterface).setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GeoTagWriter.S);
        verify(exifInterface).saveAttributes();
    }
}
