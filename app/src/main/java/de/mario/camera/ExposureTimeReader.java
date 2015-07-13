package de.mario.camera;

import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processes images after they are stored.
 *
 * @author Mario
 */
class ExposureTimeReader {


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

    Map<String, Double> readExposureTimes(String [] images) {
        return readExposureTimes(Arrays.asList(images));
    }

    Map<String, Double> readExposureTimes(List<String> images) {
        Map<String, Double> map = new HashMap<>(images.size());
        for (String path : images){
            try {
                map.put(path, getExposureTime(path));
            }catch(IOException | IllegalArgumentException exc){
                log(exc);
            }
        }
        return map;
    }

    void log(Exception exc) {
        Log.e(PhotoActivable.DEBUG_TAG, exc.getMessage());
    }
}
