package de.mario.camera;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.Override;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class InternalMemoryAccessorTest {

	private InternalMemoryAccessor classUnderTest;

	@Mock
	private Context context;

	@Before
	public void setUp(){
		classUnderTest = new InternalMemoryAccessor(context){
			@Override
			File getDirectory(){
				String path = getClass().getResource(".").getPath();
				return new File(path);
			}
		};
	}

	@Test(expected = IOException.class)
	public void testLoad() throws IOException{
		classUnderTest.load("test");
	}

}
