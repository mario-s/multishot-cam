package de.mario.camera;


import java.io.File;
import java.io.IOException;

import org.junit.*;


public class InternalMemoryAccessorTest {

	private InternalMemoryAccessor classUnderTest;

	@Before
	public void setUp(){
		String path = getClass().getResource(".").getFile();
		classUnderTest = new InternalMemoryAccessor(new File(path));
	}

	@Test(expected = IOException.class)
	public void testLoad() throws IOException{
		classUnderTest.load("test");
	}

}
