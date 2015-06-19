package de.mario.camera;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Override;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.*;

import static org.junit.Assert.*;


public class ExposureTimeReaderTest {

	private ExposureTimeReader classUnderTest;

	private String val;

	@Before
	public void setUp(){
		val = "0.1";
		List<String> names = Collections.singletonList("");
		classUnderTest = new ExposureTimeReader(names) {

			@Override
			String getExposureTimeFromExif(String pathToImage) throws IOException {
				return val;
			}

			@Override
			void log(Exception exc) {
			}
		};
	}

	@Test
	public void testReadExposureTimes_Success() {
		Map<String, Double> result = classUnderTest.readExposureTimes();
		assertFalse(result.isEmpty());
	}

	@Test
	public void testReadExposureTimes_IllegalArgument() {
		val = "";
		Map<String, Double> result = classUnderTest.readExposureTimes();
		assertTrue(result.isEmpty());
	}

}
