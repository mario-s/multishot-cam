package de.mario.camera.service;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Override;
import java.lang.System;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Core;

import de.mario.camera.service.ExposureMergeService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExposureMergeServiceTest {

	private ExposureMergeService classUnderTest;

	@Mock
	private Intent intent;

	@BeforeClass
	public static void init() {
		String arch = System.getProperty("sun.arch.data.model");
		String lib = ExposureMergeService.class.getResource("../native/x"+arch).getPath();
		File f = new File(lib);
		String libpath = System.getProperty("java.library.path");
		System.setProperty("java.library.path", libpath + ";" + f.getAbsolutePath());
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Before
	public void setUp(){
		classUnderTest = new ExposureMergeService();
	}

	private String getPath(String name){
		return getClass().getResource(name).getFile();
	}

	@Test
	public void testOnHandleIntent() {
		String [] files = new String[]{getPath("../memorial61.png"), getPath("../memorial64.png"), getPath("../memorial67.png")};
		when(intent.getStringArrayExtra(ExposureMergeService.PARAM_PICS)).thenReturn(files);
		classUnderTest.onHandleIntent(intent);
	}

}
