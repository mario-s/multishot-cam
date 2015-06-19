package de.mario.camera;

import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processes images after they are stored.
 */
class ExposureTimeReader {

    private final List<String> imagesNames;

    ExposureTimeReader(List<String> imagesNames){
        this.imagesNames = imagesNames;
    }

    String getExposureTimeFromExif(String pathToImage) throws IOException {
        ExifInterface exifInterface = new ExifInterface(pathToImage);
        return exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
    }

    private double getExposureTime(String pathToImage) throws IOException {
        String exposureTime = getExposureTimeFromExif(pathToImage);

        if(exposureTime == null || exposureTime.isEmpty()){
            throw new IllegalArgumentException("Unable to read exposure time from: " + pathToImage);
        }

        return Double.parseDouble(exposureTime);
    }

    Map<String, Double> readExposureTimes() {
        Map<String, Double> map = new HashMap<>(imagesNames.size());
        for (String name : imagesNames){
            try {
                map.put(name, getExposureTime(name));
            }catch(IOException | IllegalArgumentException exc){
                log(exc);
            }
        }
        return map;
    }

    void log(Exception exc) {
        Log.e(PhotoActivity.DEBUG_TAG, exc.getMessage());
    }
}
