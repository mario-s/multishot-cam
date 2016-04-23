package de.mario.camera.exif;

import android.media.ExifInterface;

/**
 */
public enum ExifTag {
    TAG_ORIENTATION(ExifInterface.TAG_ORIENTATION), TAG_DATETIME(ExifInterface.TAG_DATETIME),
    TAG_MAKE(ExifInterface.TAG_MAKE), TAG_MODEL(ExifInterface.TAG_MODEL),
    TAG_FLASH(ExifInterface.TAG_FLASH),
    TAG_IMAGE_WIDTH(ExifInterface.TAG_IMAGE_WIDTH),
    TAG_IMAGE_LENGTH(ExifInterface.TAG_IMAGE_LENGTH),
    TAG_EXPOSURE_TIME(ExifInterface.TAG_EXPOSURE_TIME),
    TAG_APERTURE(ExifInterface.TAG_APERTURE),
    TAG_ISO(ExifInterface.TAG_ISO),
    TAG_WHITE_BALANCE(ExifInterface.TAG_WHITE_BALANCE),
    TAG_FOCAL_LENGTH(ExifInterface.TAG_FOCAL_LENGTH),
    TAG_GPS_LATITUDE(ExifInterface.TAG_GPS_LATITUDE),
    TAG_GPS_LONGITUDE(ExifInterface.TAG_GPS_LONGITUDE),
    TAG_GPS_LATITUDE_REF(ExifInterface.TAG_GPS_LATITUDE_REF),
    TAG_GPS_LONGITUDE_REF(ExifInterface.TAG_GPS_LONGITUDE_REF),
    TAG_GPS_ALTITUDE(ExifInterface.TAG_GPS_ALTITUDE),
    TAG_GPS_ALTITUDE_REF(ExifInterface.TAG_GPS_ALTITUDE_REF),
    TAG_GPS_TIMESTAMP(ExifInterface.TAG_GPS_TIMESTAMP),
    TAG_GPS_DATESTAMP(ExifInterface.TAG_GPS_DATESTAMP),
    TAG_GPS_PROCESSING_METHOD(ExifInterface.TAG_GPS_PROCESSING_METHOD);

    String value;

    ExifTag(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
