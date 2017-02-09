package de.mario.photo.controller.shot;

import android.content.Context;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.mario.photo.glue.TaskListener;


/**
 * This class helps to write and read images to internal memory of the device.
 *
 * @author Mario
 */
class RawDataIO {
    @Deprecated
    private final List<String> internalNames;
    private final Context context;
    //TODO set in constructor
    private TaskListener listener;

    RawDataIO(Context context) {
        this.context = context;
        this.internalNames = new ArrayList<>();
    }

    void save(byte[] data, String name) {
        new SaveTask(context, data, name).execute();
    }

    /**
     * Loads the content from the internal storage an returns it as a byte array.
     * @param name name of the File.
     * @return content as a byte array.
     * @throws IOException
     */
    byte[] load(String name) throws IOException {

        FileInputStream inputStream = context.openFileInput(name);
        ByteArrayOutputStream os = new ByteArrayOutputStream(2048);
        byte[] buff = new byte[1024]; //buffer size.
        for (int i; -1 != (i = inputStream.read(buff)); os.write(buff, 0, i)) ;
        inputStream.close();
        return os.toByteArray();
    }

    /**
     * Moves all internal saved images to the given directory. This can be on an external storage.
     *
     * @param targetDirectory directory on an external storage
     * @return the paths of the images
     */
    @Deprecated
    Collection<String> moveAll(String targetDirectory) throws IOException{
        List<String> imageNames = new ArrayList<>(internalNames.size());
        for (String name : internalNames) {
            File target = write(targetDirectory, name);
            if (target != null) {
                imageNames.add(target.getPath());
                context.deleteFile(name);
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

    private class SaveTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private byte[] data;
        private String name;

        SaveTask(Context context, byte[] data, String name) {
            this.context = context;
            this.data = data;
            this.name = name;
        }

        private synchronized void addName(String name) {
            internalNames.add(name);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                //TODO save in the picture file directory
                FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
                fos.write(data);
                fos.close();
                addName(name);
            } catch (IOException exc) {
                throw new IllegalStateException(exc);
            }

            return name;
        }

        @Override
        protected void onPostExecute(String name) {
            if (listener != null) {
                listener.onFinish(name);
            }
        }
    }

}
