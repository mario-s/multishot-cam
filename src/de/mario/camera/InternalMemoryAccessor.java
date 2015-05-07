package de.mario.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * This class helps to write and read images to internal memory of the device.
 * 
 * @author Mario
 * 
 */
class InternalMemoryAccessor {
	private final Context context;
	
	public InternalMemoryAccessor(Context context) {
		this.context = context;
	}
	
	String saveToInternalSorage(Bitmap bitmapImage)
			throws IOException {
		ContextWrapper cw = new ContextWrapper(context);
		// path to /data/data/yourapp/app_data/imageDir
		File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		// Create imageDir
		File mypath = new File(directory, "profile.jpg");

		FileOutputStream fos = new FileOutputStream(mypath);

		// Use the compress method on the BitMap object to write image to
		// the OutputStream
		bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		fos.close();
		return directory.getAbsolutePath();
	}

	Bitmap loadImageFromStorage(String path) throws FileNotFoundException {

		File f = new File(path, "profile.jpg");
		return BitmapFactory.decodeStream(new FileInputStream(f));
	}

}
