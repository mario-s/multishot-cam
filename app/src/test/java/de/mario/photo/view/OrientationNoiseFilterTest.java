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
    public void test_horizontal() {
        int result = classUnderTest.filter(275, 270);
        assertEquals(271, result);
    }

    @Test
    public void test_vertical_toLeft() {
        int result = classUnderTest.filter(358, 6);
        assertEquals(3, result);
    }

    @Test
    public void test_vertical_toRight() {
        int result = classUnderTest.filter(6, 358);
        assertEquals(0, result);
    }
}
