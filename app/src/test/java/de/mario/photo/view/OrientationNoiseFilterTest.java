package de.mario.photo.view;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OrientationNoiseFilterTest {

    private OrientationNoiseFilter classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new OrientationNoiseFilter();
    }


    @Test
    public void testFilter_horizontal() {
        classUnderTest.filter(270);
        int result = classUnderTest.filter(275);
        assertEquals(271, result);
    }

    @Test
    public void testFilter_vertical_toLeft() {
        classUnderTest.filter(6);
        int result = classUnderTest.filter(358);
        assertEquals(3, result);
    }

    @Test
    public void testFilter_vertical_toRight() {
        classUnderTest.filter(358);
        int result = classUnderTest.filter(6);
        assertEquals(0, result);
    }
}
