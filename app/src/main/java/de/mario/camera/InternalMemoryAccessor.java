package de.mario.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.content.Context;
import android.content.ContextWrapper;


/**
 * This class helps to write and read images to internal memory of the device.
 * 
 * @author Mario
 * 
 */
class InternalMemoryAccessor {

	private final Context context;
	
	InternalMemoryAccessor(Context context) {
		this.context = context;
	}
	
	void save(byte[] data, String name)
			throws IOException {
		// Create imageDir
		File directory = getDirectory();
		File pictureFile = new File(directory, name);
		FileOutputStream fos = new FileOutputStream(pictureFile);
		fos.write(data);
		fos.close();

	}
	
	byte[] load(String name) throws IOException {

		File f = new File(getDirectory(), name);
		RandomAccessFile file = new RandomAccessFile(f.getAbsolutePath(), "r");
		byte[] b = new byte[(int)file.length()];
		file.read(b);
		file.close();
		return b;
	}

	File getDirectory() {
		ContextWrapper cw = new ContextWrapper(context);
		// path to
		return cw.getDir("data", Context.MODE_PRIVATE);
	}



}
