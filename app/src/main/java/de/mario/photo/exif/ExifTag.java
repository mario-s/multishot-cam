package de.mario.photo.exif;

import android.media.ExifInterface;

/**
 */
public final class ExifTag {
    public static final String ORIENTATION = ExifInterface.TAG_ORIENTATION;
    public static final String DATETIME = ExifInterface.TAG_DATETIME;
    public static final String MAKE = ExifInterface.TAG_MAKE;
    public static final String MODEL = ExifInterface.TAG_MODEL;
    public static final String FLASH = ExifInterface.TAG_FLASH;
    public static final String IMAGE_WIDTH = ExifInterface.TAG_IMAGE_WIDTH;
    public static final String IMAGE_LENGTH = ExifInterface.TAG_IMAGE_LENGTH;
    public static final String EXPOSURE_TIME = ExifInterface.TAG_EXPOSURE_TIME;
    public static final String APERTURE = ExifInterface.TAG_APERTURE;
    public static final String ISO = ExifInterface.TAG_ISO;
    public static final String WHITE_BALANCE = ExifInterface.TAG_WHITE_BALANCE;
    public static final String FOCAL_LENGTH = ExifInterface.TAG_FOCAL_LENGTH;
    public static final String GPS_LATITUDE = ExifInterface.TAG_GPS_LATITUDE;
    public static final String GPS_LONGITUDE = ExifInterface.TAG_GPS_LONGITUDE;
    public static final String GPS_LATITUDE_REF = ExifInterface.TAG_GPS_LATITUDE_REF;
    public static final String GPS_LONGITUDE_REF = ExifInterface.TAG_GPS_LONGITUDE_REF;
    public static final String GPS_ALTITUDE = ExifInterface.TAG_GPS_ALTITUDE;
    public static final String GPS_ALTITUDE_REF = ExifInterface.TAG_GPS_ALTITUDE_REF;
    public static final String GPS_TIMESTAMP = ExifInterface.TAG_GPS_TIMESTAMP;
    public static final String GPS_DATESTAMP = ExifInterface.TAG_GPS_DATESTAMP;
    public static final String GPS_PROCESSING_METHOD = ExifInterface.TAG_GPS_PROCESSING_METHOD;

    public static String[] values() {
        return new String[]{ORIENTATION, DATETIME, MAKE, MODEL, FLASH, IMAGE_WIDTH, IMAGE_LENGTH,
                EXPOSURE_TIME, APERTURE, ISO, WHITE_BALANCE, FOCAL_LENGTH, GPS_LATITUDE, GPS_LONGITUDE,
                GPS_LATITUDE_REF, GPS_LONGITUDE_REF, GPS_ALTITUDE, GPS_ALTITUDE_REF, GPS_TIMESTAMP,
                GPS_DATESTAMP, GPS_PROCESSING_METHOD};
    }
}
