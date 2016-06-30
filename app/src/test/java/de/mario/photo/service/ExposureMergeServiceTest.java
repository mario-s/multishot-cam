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

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	}


	@Test
	public void testOnHandleIntent() {
		String[] files = new String[]{"a.jpg", "b.jpg", "c.jpg"};
		when(intent.getStringArrayExtra(ExposureMergeService.PARAM_PICS)).thenReturn(files);
		classUnderTest.onHandleIntent(intent);
		verify(merger).merge(anyList());
	}

}
