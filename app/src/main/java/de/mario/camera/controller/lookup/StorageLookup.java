package de.mario.camera.controller.lookup;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;

/**
 * This class encapsulates the search for directories where to store files.
 */
public class StorageLookup implements StorageLookable {
    public static final String MULTI = "100_MULTI";
    private final Context context;

    private File pictureSaveDirectory;

    public StorageLookup(Context context) {
        this.context = context;
    }

    @Override
    public File lookupSaveDirectory() {
        if(pictureSaveDirectory == null){
            pictureSaveDirectory = createStorageDirectory();
        }
        return pictureSaveDirectory;
    }

    private File createStorageDirectory() {
        File sdDcimDir = findRealSdDirectory();
        //TODO directories according to DCF
        File directory = new File(sdDcimDir, MULTI);
        if(!directory.exists()){
            directory.mkdir();
            //check again if it exists now, otherwise go back to emulated
            if(!directory.exists()){
                directory = new File(getDcimStorageDirectory(), MULTI);
                if(!directory.exists()) {
                    directory.mkdir();
                }
            }
        }
        return directory;
    }

    private File findRealSdDirectory() {
        File dcimDir = getDcimStorageDirectory(); //this contains normally the emulated directory
        File emulated = findEmulated(dcimDir); //search recursive for the emulated node in the path
        if(emulated != null) {//if not we can skip the rest
            File[] privateDirectories = getPrivateDirectories(); //private dirs on sd and or emulated
            for (File privateDir : privateDirectories) {
                //should be the path on the sd
                if (!privateDir.getPath().startsWith(emulated.getPath())) {
                    //find the path to Android directory on the SD
                    File sdAndroid = findAndroidOnSd(privateDir);
                    if (sdAndroid != null) {
                        dcimDir = new File(sdAndroid.getParentFile(), "DCIM");
                    }
                }
            }
        }

        return dcimDir;
    }

    /**
     * walk the tree up till there is no Android at the end of the path
     */
    private File findAndroidOnSd(File current) {
        DirectoryWalker walker = new DirectoryWalker("Android", current);
        return walker.walkUp();
    }

    private File findEmulated(File current) {
        DirectoryWalker walker = new DirectoryWalker("emulated", current);
        return walker.walkUp();
    }

    File[] getPrivateDirectories() {
        return ContextCompat.getExternalFilesDirs(context, null);
    }

    File getDcimStorageDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    boolean isExternalStorageWritable() {
        String state = getStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    String getStorageState() {
        return Environment.getExternalStorageState();
    }

    private class DirectoryWalker {
        private File start;
        private File result;
        private String pathSuffix;

        DirectoryWalker(String suffix, File start) {
            this.pathSuffix = suffix;
            this.start = start;
        }

        File walkUp() {
            walkUp(start);
            return result;
        }

        /**
         * walk the tree up till there is no android in the path
         */
        private void walkUp(File current) {
            File parent = current.getParentFile();
            if(!parent.getPath().endsWith(pathSuffix)){
                walkUp(parent);
            }else{
                result = parent;
            }
        }
    }

}
