package de.mario.photo.controller.shot;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.settings.SettingsAccess;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoShotsFactoryTest {

    @Mock
    private Camera.Parameters parameters;
    @Mock
    private SettingsAccess settings;

    private PhotoShotsFactory classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new PhotoShotsFactory(parameters);
        given(parameters.getMinExposureCompensation()).willReturn(-4);
        given(parameters.getMaxExposureCompensation()).willReturn(4);
        given(settings.getExposureSequenceType()).willReturn(0);
        given(settings.isLastFlash()).willReturn(true);
    }

    @Test
    public void testSequence_WithFlash() {
        Shot[] shots = classUnderTest.create(settings);

        assertEquals(4, shots.length);
    }

}
