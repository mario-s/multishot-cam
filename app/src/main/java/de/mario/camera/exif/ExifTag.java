package de.mario.camera.exif;

import android.media.ExifInterface;

/**
 */
public enum ExifTag {
    ORIENTATION(ExifInterface.TAG_ORIENTATION), DATETIME(ExifInterface.TAG_DATETIME),
    MAKE(ExifInterface.TAG_MAKE), MODEL(ExifInterface.TAG_MODEL),
    FLASH(ExifInterface.TAG_FLASH),
    IMAGE_WIDTH(ExifInterface.TAG_IMAGE_WIDTH),
    IMAGE_LENGTH(ExifInterface.TAG_IMAGE_LENGTH),
    EXPOSURE_TIME(ExifInterface.TAG_EXPOSURE_TIME),
    APERTURE(ExifInterface.TAG_APERTURE),
    ISO(ExifInterface.TAG_ISO),
    WHITE_BALANCE(ExifInterface.TAG_WHITE_BALANCE),
    FOCAL_LENGTH(ExifInterface.TAG_FOCAL_LENGTH),
    GPS_LATITUDE(ExifInterface.TAG_GPS_LATITUDE),
    GPS_LONGITUDE(ExifInterface.TAG_GPS_LONGITUDE),
    GPS_LATITUDE_REF(ExifInterface.TAG_GPS_LATITUDE_REF),
    GPS_LONGITUDE_REF(ExifInterface.TAG_GPS_LONGITUDE_REF),
    GPS_ALTITUDE(ExifInterface.TAG_GPS_ALTITUDE),
    GPS_ALTITUDE_REF(ExifInterface.TAG_GPS_ALTITUDE_REF),
    GPS_TIMESTAMP(ExifInterface.TAG_GPS_TIMESTAMP),
    GPS_DATESTAMP(ExifInterface.TAG_GPS_DATESTAMP),
    GPS_PROCESSING_METHOD(ExifInterface.TAG_GPS_PROCESSING_METHOD);

    String value;

    ExifTag(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
