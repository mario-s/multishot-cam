package de.mario.photo.support;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageOpenerTest {
    @Mock
    private Context context;
    @Mock
    private PackageManager packageManager;
    @Mock
    private Intent intent;
    @Mock
    private ComponentName componentName;

    private ImageOpener classUnderTest;

    private File file;

    @Before
    public void setUp() {
        classUnderTest = new ImageOpener(context) {
            @Override
            Intent newIntent() {
                return intent;
            }
        };

        file = new File(".");
        given(context.getPackageManager()).willReturn(packageManager);
    }

    @Test
    public void testOpen_UriExists() {
        given(intent.resolveActivity(packageManager)).willReturn(componentName);
        classUnderTest.open(file);
        verify(context).startActivity(intent);
    }
}
