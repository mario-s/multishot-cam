package de.mario.photo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.glue.CameraControlable;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class CameraControllerProviderTest {

    @Mock
    private StorageLookable storageLookable;
    @Mock
    private CameraController controller;

    private CameraControllerProvider classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new CameraControllerProvider(){
            @Override
            CameraController newController() {
                return controller;
            }
        };

        setInternalState(classUnderTest, "storageLookable", storageLookable);
    }

    @Test
    public void testGet() {
        CameraControlable result = classUnderTest.get();
        assertNotNull(result);
        verify(controller).setStorageLookup(storageLookable);
    }
}
