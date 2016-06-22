package de.mario.camera.controller.support;

import android.hardware.Camera.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class IsoSupportTest {

    @Mock
    private Parameters parameters;
    @InjectMocks
    private IsoSupport classUnderTest;

    @Test
    public void testGetIsoValues(){
        String[] vals = classUnderTest.getIsoValues();
        assertNotNull(vals);
    }
}
