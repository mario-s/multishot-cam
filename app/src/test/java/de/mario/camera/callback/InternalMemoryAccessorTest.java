package de.mario.camera.callback;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;


public class InternalMemoryAccessorTest {

	private InternalMemoryAccessor classUnderTest;

	private File testFile;

	@Before
	public void setUp(){
		String path = getClass().getResource(".").getFile();
		testFile = new File(path, "test.dat");
		classUnderTest = new InternalMemoryAccessor(new File(path));
	}

	@After
	public void shutDown() {
		if(testFile.exists()){
			testFile.delete();
		}
	}

	@Test(expected = IOException.class)
	public void testLoad() throws IOException{
		classUnderTest.load("test");
	}

	@Test
	public void testSave() throws IOException {
		classUnderTest.save("test".getBytes(), testFile.getName());
		assertTrue(testFile.exists());
	}

}
