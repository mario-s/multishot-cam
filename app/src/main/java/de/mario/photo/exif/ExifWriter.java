package de.mario.photo.exif;

import android.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import roboguice.util.Ln;

/**
 */
public class ExifWriter {

    public void addTags(File source, Map<String, String> tags) {
        if (!tags.isEmpty()) {
            try {
                ExifInterface sourceExif = getExifInterface(source);
                ExifInterface targetExif = getExifInterface(source);
                copy(sourceExif, targetExif); //no to loose the existing metadata

                for (Map.Entry<String, String> tag : tags.entrySet()) {
                    String key = tag.getKey();
                    String val = tag.getValue();
                    targetExif.setAttribute(key, val);
                }

                targetExif.saveAttributes();
            } catch (IOException exc) {
                Ln.w(exc);
            }
        }
    }

    public void copy(File source, File target) {
        try {
            ExifInterface sourceExif = getExifInterface(source);
            ExifInterface targetExif = getExifInterface(target);
            copy(sourceExif, targetExif);

            targetExif.saveAttributes();
        }catch (IOException exc) {
            Ln.w(exc);
        }
    }

    private void copy(ExifInterface sourceExif, ExifInterface targetExif) {
        for (String tag : ExifTag.values()) {
            String attr = sourceExif.getAttribute(tag);
            if(attr != null) {
                targetExif.setAttribute(tag, attr);
            }
        }
    }

    ExifInterface getExifInterface(File file) throws IOException {
        return new ExifInterface(file.getAbsolutePath());
    }
}
