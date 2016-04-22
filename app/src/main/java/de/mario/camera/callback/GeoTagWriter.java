package de.mario.camera.callback;

import android.location.Location;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import de.mario.camera.PhotoActivable;

/**
 * Writes some exif data to the image.
 */
final class GeoTagWriter implements ExifTagWriteable{
    private Location location;

    GeoTagWriter(Location location){
        this.location = location;
    }

    @Override
    public void setTag(File file) {

            try {
                ExifInterface exif = new ExifInterface(file.getAbsolutePath());

                double latitude = Math.abs(location.getLatitude());
                double longitude = Math.abs(location.getLongitude());

                int num1Lat = (int) Math.floor(latitude);
                int num2Lat = (int) Math.floor((latitude - num1Lat) * 60);
                double num3Lat = (latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;

                int num1Lon = (int) Math.floor(longitude);
                int num2Lon = (int) Math.floor((longitude - num1Lon) * 60);
                double num3Lon = (longitude - ((double) num1Lon + ((double) num2Lon / 60))) * 3600000;

                String lat = num1Lat + "/1," + num2Lat + "/1," + num3Lat + "/1000";
                String lon = num1Lon + "/1," + num2Lon + "/1," + num3Lon + "/1000";

                if (location.getLatitude() > 0) {
                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
                } else {
                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
                }

                if (location.getLongitude() > 0) {
                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
                } else {
                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
                }

                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, lat);
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, lon);

                exif.saveAttributes();
            }catch(IOException ex) {
                Log.w(PhotoActivable.DEBUG_TAG, ex);
            }
    }
}
