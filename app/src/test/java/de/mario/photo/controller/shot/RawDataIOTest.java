package de.mario.photo.controller.shot;


import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RawDataIOTest {
	private static final String NAME = "test";

	@Mock
	private Context context;

	@Mock
	private FileInputStream inputStream;

	@InjectMocks
	private RawDataIO classUnderTest;

	@Before
	public void setUp() throws IOException {
		given(context.openFileInput(NAME)).willReturn(inputStream);
		given(inputStream.read(any(byte[].class))).willReturn(-1);
	}


	@Test
	public void testLoad() throws IOException{
		classUnderTest.load("test");
	}

	@Test
	public void testSave() throws IOException {
		classUnderTest.save(NAME.getBytes(), NAME);
		//TODO verify if interface was called
	}

}
