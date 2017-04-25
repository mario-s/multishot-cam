package de.mario.photo.view;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class OrientationNoiseFilterTest {

    private static final BigDecimal ERROR = new BigDecimal(1);

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
        assertThat(new BigDecimal(result), is(closeTo(new BigDecimal(3), ERROR)));
    }

    @Test
    public void testFilter_vertical_toRight() {
        classUnderTest.filter(358);
        int result = classUnderTest.filter(6);
        BigDecimal expected = new BigDecimal(result % OrientationNoiseFilter.MAX);
        assertThat(new BigDecimal(result), is(closeTo(expected, ERROR)));
    }
}
