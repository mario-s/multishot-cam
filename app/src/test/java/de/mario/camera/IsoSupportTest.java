package de.mario.camera;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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
        List<String> vals = classUnderTest.getIsoValues();
        assertNotNull(vals);
    }
}
