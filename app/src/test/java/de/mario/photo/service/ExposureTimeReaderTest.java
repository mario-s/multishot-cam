package de.mario.photo.service;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ExposureTimeReaderTest {

	private ExposureTimeReader classUnderTest;

	private String val;

	private List<String> names;

	@Before
	public void setUp(){
		val = "0.1";
		names = Collections.singletonList("test");
		classUnderTest = new ExposureTimeReader() {

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
		Map<String, Double> result = classUnderTest.readExposureTimes(names);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testReadExposureTimes_IllegalArgument() {
		val = "";
		Map<String, Double> result = classUnderTest.readExposureTimes(names);
		assertTrue(result.isEmpty());
	}

}
