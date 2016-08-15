package de.mario.photo.controller;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HdrProcessControllerTest {
    @Mock
    private Context context;

    private HdrProcessController classUnderTest;

    private String[] testData;

    private OpenCvLoaderCallback testCallBack;

    @Before
    public void setUp() {
        testData = new String[]{"foo"};
        classUnderTest = new HdrProcessController(context) {
            @Override
            void callLoader(OpenCvLoaderCallback callback) {
                testCallBack = callback;
            }
        };
    }

    @Test
    public void testProcess() {
        classUnderTest.process(testData);
        assertThat(testCallBack, notNullValue());
    }
}
