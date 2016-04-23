package de.mario.camera.exif;

import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import de.mario.camera.PhotoActivable;

/**
 */
public class ExifWriter {

    public void addTags(File source, Map<ExifTag, String> tags) {
        try {
            ExifInterface sourceExif = getExifInterface(source);
            ExifInterface targetExif = getExifInterface(source);
            copy(sourceExif, targetExif); //no to loose the existing metadata

            for(Map.Entry<ExifTag, String> tag : tags.entrySet()) {
                String key = tag.getKey().getValue();
                String val = tag.getValue();
                targetExif.setAttribute(key, val);
            }

            targetExif.saveAttributes();
        }catch (IOException exc) {
            Log.w(PhotoActivable.DEBUG_TAG, exc);
        }
    }

    public void copy(File source, File target) {
        try {
            ExifInterface sourceExif = getExifInterface(source);
            ExifInterface targetExif = getExifInterface(target);
            copy(sourceExif, targetExif);

            targetExif.saveAttributes();
        }catch (IOException exc) {
            Log.w(PhotoActivable.DEBUG_TAG, exc);
        }
    }

    private void copy(ExifInterface sourceExif, ExifInterface targetExif) {
        for (ExifTag tag : ExifTag.values()){
            String attr = sourceExif.getAttribute(tag.getValue());
            if(attr != null) {
                targetExif.setAttribute(tag.getValue(), attr);
            }
        }
    }

    ExifInterface getExifInterface(File file) throws IOException {
        return new ExifInterface(file.getAbsolutePath());
    }
}
