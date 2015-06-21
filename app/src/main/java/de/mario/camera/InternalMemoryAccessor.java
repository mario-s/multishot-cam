package de.mario.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;


/**
 * This class helps to write and read images to internal memory of the device.
 *
 * @author Mario
 */
class InternalMemoryAccessor {

    private final Context context;
    private final List<String> internalNames;

    InternalMemoryAccessor(Context context) {
        this.context = context;
        this.internalNames = new ArrayList<>();
    }

    void save(byte[] data, String name)
            throws IOException {
        // Create imageDir
        File directory = getDirectory();
        File pictureFile = new File(directory, name);
        FileOutputStream fos = new FileOutputStream(pictureFile);
        fos.write(data);
        fos.close();

        internalNames.add(name);
    }

    byte[] load(String name) throws IOException {

        File f = new File(getDirectory(), name);
        RandomAccessFile file = new RandomAccessFile(f.getAbsolutePath(), "r");
        byte[] b = new byte[(int) file.length()];
        file.read(b);
        file.close();
        return b;
    }

    File getDirectory() {
        ContextWrapper cw = new ContextWrapper(context);
        // path to
        return cw.getDir("data", Context.MODE_PRIVATE);
    }

    /**
     * Copies all internal saved images to the given directory. This can be on an external storage.
     *
     * @param pictureFileDir directory on an external storage
     * @return the pathes of the images
     */
    Collection<String> copyAll(String pictureFileDir) throws IOException{
        List<String> imageNames = new ArrayList<>(internalNames.size());
        for (String internal : internalNames) {
            File target = write(pictureFileDir, internal);
            if (target != null) {
                imageNames.add(target.getPath());
            }
        }
        return imageNames;
    }


    private File write(String pictureFileDir, String name) throws IOException{
        File pictureFile = null;
        FileOutputStream fos = null;
        try {
            pictureFile = new File(pictureFileDir, name);
            fos = new FileOutputStream(pictureFile);
            fos.write(load(name));
        } finally {
            if(fos != null){
                fos.close();
            }
        }
        return pictureFile;
    }

}
