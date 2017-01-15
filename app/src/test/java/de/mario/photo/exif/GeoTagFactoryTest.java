package de.mario.photo.exif;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class GeoTagFactoryTest {

    @Mock
    private Location location;

    private GeoTagFactory classUnderTest;

    @Before
    public void setUp() {

        classUnderTest = new GeoTagFactory();
    }

    @Test
    public void create() throws IOException {
        Map<String, String> result = classUnderTest.create(location);
        assertThat(result.isEmpty(), is(false));
    }
}
