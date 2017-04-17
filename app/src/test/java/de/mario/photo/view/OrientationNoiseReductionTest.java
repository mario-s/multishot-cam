package de.mario.photo.view;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OrientationNoiseReductionTest {

    private OrientationNoiseReduction classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new OrientationNoiseReduction();
    }

    @Test
    public void test_horizontal() {
        int result = classUnderTest.reduce(275, 270);
        assertEquals(271, result);
    }

    @Test
    public void test_vertical_toLeft() {
        int result = classUnderTest.reduce(358, 6);
        assertEquals(3, result);
    }

    @Test
    public void test_vertical_toRight() {
        int result = classUnderTest.reduce(6, 358);
        assertEquals(0, result);
    }
}
