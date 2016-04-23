package de.mario.camera.service;

import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import de.mario.camera.PhotoActivable;

/**
 */
class ExifCopy {

    private static final String [] TAGS = new String [] {ExifInterface.TAG_ORIENTATION,
            ExifInterface.TAG_DATETIME, ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL,
            ExifInterface.TAG_FLASH, ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.TAG_IMAGE_LENGTH,
            ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE_REF,
            ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_APERTURE,
            ExifInterface.TAG_ISO, ExifInterface.TAG_GPS_ALTITUDE,
            ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_TIMESTAMP,
            ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_WHITE_BALANCE,
            ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_GPS_PROCESSING_METHOD};

    void copy(File source, File target) {
        try {
            ExifInterface sourceExif = getExifInterface(source);
            ExifInterface targetExif = getExifInterface(target);
            for (String tag : TAGS){
                String attr = sourceExif.getAttribute(tag);
                targetExif.setAttribute(tag, attr);
            }

            targetExif.saveAttributes();
        }catch (IOException exc) {
            Log.w(PhotoActivable.DEBUG_TAG, exc);
        }
    }

    ExifInterface getExifInterface(File file) throws IOException {
        return new ExifInterface(file.getAbsolutePath());
    }
}
