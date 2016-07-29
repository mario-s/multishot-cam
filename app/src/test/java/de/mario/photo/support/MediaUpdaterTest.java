package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaUpdaterTest {
    @Mock
    private Context context;
    @InjectMocks
    private MediaUpdater classUnderTest;

    private File file;

    @Before
    public void setUp() {
        file = new File(".");
    }

    @Test
    public void testSendUpdate() {
        classUnderTest.sendUpdate(file);
        verify(context).sendBroadcast(any(Intent.class));
    }
}
