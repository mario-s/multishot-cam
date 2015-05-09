package de.mario.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore.Files;


/**
 * This class helps to write and read images to internal memory of the device.
 * 
 * @author Mario
 * 
 */
final class InternalMemoryAccessor {
	private static final String DIR = "/data/data/multishot/app_data/image";
	private final Context context;
	
	InternalMemoryAccessor(Context context) {
		this.context = context;
	}
	
	void save(byte[] data, String name)
			throws IOException {
		// Create imageDir
		File pictureFile = new File(getDirectory(), name);
		FileOutputStream fos = new FileOutputStream(pictureFile);
		fos.write(data);
		fos.close();

	}
	
	byte[] load(String name) throws IOException {

		File f = new File(getDirectory(), name);
		RandomAccessFile file = new RandomAccessFile(f.getAbsolutePath(), "r");
		byte[] b = new byte[(int)file.length()];
		file.read(b);
		return b;
	}

	private File getDirectory() {
		ContextWrapper cw = new ContextWrapper(context);
		// path to 
		return cw.getDir(DIR, Context.MODE_PRIVATE);
	}



}
