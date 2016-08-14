package de.mario.photo.service;

import android.app.NotificationManager;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Mat;

import java.io.File;

import de.mario.photo.settings.SettingsAccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(MockitoJUnitRunner.class)
public class ExposureMergeServiceTest {

	private ExposureMergeService classUnderTest;

	@Mock
	private Merger merger;

	@Mock
	private Intent intent;

	@Mock
	private Mat mat;

	@Mock
	private SettingsAccess settingsAccess;

	@Mock
	private NotificationManager notificationManager;

	@Before
	public void setUp(){
		classUnderTest = new ExposureMergeService(merger) {
			@Override
			Mat read(String path) {
				return mat;
			}

			@Override
			void write(Mat fusion, File out) {
			}

			@Override
			public Object getSystemService(String name) {
				return notificationManager;
			}
		};
		setInternalState(classUnderTest, "settingsAccess", settingsAccess);
	}


	@Test
	public void testOnHandleIntent() {

		given(settingsAccess.isEnabled(anyInt())).willReturn(true);
		String[] files = new String[]{"a.jpg", "b.jpg", "c.jpg"};
		given(intent.getStringArrayExtra(ExposureMergeService.PARAM_PICS)).willReturn(files);
		classUnderTest.onHandleIntent(intent);
		verify(merger).merge(anyList());
	}

}
