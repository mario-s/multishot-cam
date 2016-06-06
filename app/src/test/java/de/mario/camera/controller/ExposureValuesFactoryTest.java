package de.mario.camera.controller;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ExposureValuesFactoryTest {

    @Mock
    private Camera.Parameters parameters;

    private ExposureValuesFactory classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ExposureValuesFactory(parameters);
    }

    @Test
    public void testSequence_One() {
        List<Integer> vals = classUnderTest.getValues(0);

        assertEquals(3, vals.size());
    }
}
