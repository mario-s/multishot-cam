package de.mario.photo.controller.shot;

import android.hardware.Camera;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

import de.mario.photo.settings.SettingsAccess;

/**
 * This exposureValuesFactory creates a sequence of {@link Shot}.
 */
class PhotoShotsFactory {
    private static final String PATTERN = "yyyy-MM-dd_HH:mm:ss";
    private ExposureValuesFactory exposureValuesFactory;

    PhotoShotsFactory(Camera camera) {
        this(camera.getParameters());
    }

    PhotoShotsFactory(Camera.Parameters parameters) {
        exposureValuesFactory = new ExposureValuesFactory(parameters);
    }

    Shot[] create(SettingsAccess settings) {

        int seqType = settings.getExposureSequenceType();
        boolean lastFlash = settings.isLastFlash();

        return createEntries(exposureValuesFactory.getValues(seqType), lastFlash);
    }

    private Shot[] createEntries(Queue<Integer> els, boolean lastFlash) {

        Date date = new Date();
        int size = els.size();
        int m = lastFlash ? size + 1 : size;
        Shot[] shots = new Shot[m];
        for (int i = 0; i < m; i++) {
            shots[i] = new Shot(createFileName(date, i));
            Integer el = els.poll();
            if(el != null) {
                shots[i].setExposure(el);
            }else if(lastFlash){
                shots[i].setFlash(true);
            }
        }

        return shots;
    }

    private String createFileName(Date date, int index) {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        //TODO pattern according to DCF
        return String.format("DSC_%s_%s.jpg", dateFormat.format(date), index);
    }

}
