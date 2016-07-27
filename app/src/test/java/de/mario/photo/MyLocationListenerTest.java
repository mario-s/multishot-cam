package de.mario.photo;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class MyLocationListenerTest {
    private MyLocationListener classUnderTest;

    @Mock
    private Location location;

    @Before
    public void setUp() {
        classUnderTest = new MyLocationListener();
    }

    @Test
    public void testOnLoactionChanged_CurrentIsInput() {
        classUnderTest.onLocationChanged(location);
        Location result = classUnderTest.getCurrentLocation();
        assertThat(result, equalTo(location));
    }
}
