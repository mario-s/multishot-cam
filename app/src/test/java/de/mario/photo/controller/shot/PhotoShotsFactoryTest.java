package de.mario.photo.controller.shot;

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.settings.SettingsAccess;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
    }

    @Test
    public void testSequence_WithLastFlash_ShouldReturnFour() {
        given(settings.isLastFlash()).willReturn(true);

        Shot[] shots = classUnderTest.create(settings);

        assertEquals(4, shots.length);
    }

    @Test
    public void testSequence_WithNoFlash_ShouldReturnTree() {

        Shot[] shots = classUnderTest.create(settings);

        assertEquals(3, shots.length);

        for (Shot shot: shots ) {
            assertThat(shot.isFlash(), is(false));
        }
    }

}
