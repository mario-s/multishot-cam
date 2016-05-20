package de.mario.camera.support;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class IsoSupportTest {

    @Mock
    private Camera.Parameters parameters;

    private IsoSupport classUnderTest;

    @Before
    public void setUp(){
        classUnderTest = new IsoSupport(parameters);
    }

    @Test
    public void testGetIsoValues(){
        String[] vals = classUnderTest.getIsoValues();
        assertNotNull(vals);
    }
}
