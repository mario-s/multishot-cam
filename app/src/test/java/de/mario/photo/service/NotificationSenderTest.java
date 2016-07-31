package de.mario.photo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationSenderTest {
    public static final String TEST = "test";
    @Mock
    private Context context;
    @Mock
    private NotificationManager notificationManager;

    private NotificationSender classUnderTest;

    @Before
    public void setUp() {
        given(context.getString(anyInt())).willReturn(TEST);
        given(context.getSystemService(Context.NOTIFICATION_SERVICE)).willReturn(notificationManager);
        classUnderTest = new NotificationSender(context);
    }

    @Test
    public void testSend() {
        classUnderTest.send(TEST);
        verify(notificationManager).notify(anyInt(), any(Notification.class));
    }
}
