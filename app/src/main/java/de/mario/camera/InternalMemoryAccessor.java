package de.mario.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class helps to write and read images to internal memory of the device.
 *
 * @author Mario
 */
class InternalMemoryAccessor {

    private final File internalDirectory;
    private final List<String> internalNames;

    InternalMemoryAccessor(File internalDirectory) {
        this.internalDirectory = internalDirectory;
        this.internalNames = new ArrayList<>();
    }

    void save(byte[] data, String name)
            throws IOException {
        // Create imageDir
        FileOutputStream fos = new FileOutputStream(createInternalFile(name));
        fos.write(data);
        fos.close();

        internalNames.add(name);
    }

    /**
     * Loads the content ifrom the internal storage an returns it as a byte array.
     * @param name name of the File.
     * @return content as a byte array.
     * @throws IOException
     */
    byte[] load(String name) throws IOException {

        RandomAccessFile file = new RandomAccessFile(createInternalFile(name).getAbsolutePath(), "r");
        byte[] b = new byte[(int) file.length()];
        file.read(b);
        file.close();
        return b;
    }

    private File createInternalFile(String name) {
        return new File(internalDirectory, name);
    }

    /**
     * Moves all internal saved images to the given directory. This can be on an external storage.
     *
     * @param targetDirectory directory on an external storage
     * @return the pathes of the images
     */
    Collection<String> moveAll(String targetDirectory) throws IOException{
        List<String> imageNames = new ArrayList<>(internalNames.size());
        for (String name : internalNames) {
            File target = write(targetDirectory, name);
            if (target != null) {
                imageNames.add(target.getPath());
                createInternalFile(name).delete();
            }
        }
        return imageNames;
    }


    private File write(String targetDirectory, String name) throws IOException{
        File target = null;
        FileOutputStream fos = null;
        try {
            target = new File(targetDirectory, name);
            fos = new FileOutputStream(target);
            fos.write(load(name));
        } finally {
            if(fos != null){
                fos.close();
            }
        }
        return target;
    }

}
