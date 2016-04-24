package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Queue;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;

import static de.mario.camera.callback.ExposureUpdater.resetExposure;
import static de.mario.camera.callback.ExposureUpdater.setExposureCompensation;

/**
 * Command which will be executed to take pictures.
 */
public class PhotoCommand implements Runnable{
    private static final String PATTERN = "yyyy-MM-dd_HH:mm:ss";

    private final PhotoActivable activity;
    private final Camera camera;
    private final PhotoParams photoParams;

    public PhotoCommand(PhotoActivable activity, Camera camera){
        this.activity = activity;
        this.camera = camera;
        this.photoParams = new PhotoParams(activity);
    }

    @Override
    public void run() {
        if (!activity.getPicturesDirectory().exists()) {
            toast(activity.getResource(R.string.no_directory));
            return;
        }

        Entry<String, Integer>[] entries = createEntries(activity.getExposureValues());
        //prepare the camera for the first exposure value
        if(entries.length > 0) {
            setExposureCompensation(camera, entries[0].getValue());
        }else{
            resetExposure(camera);
        }
        photoParams.setPhotosWithEv(entries);
        ContinuesCallback callback = new ContinuesCallback(photoParams);
        camera.takePicture(new ShutterCallback(), new LoggingPictureCallback(), callback);
    }

    private void toast(String msg) {
        MessageSender sender = new MessageSender(activity.getHandler());
        sender.toast(msg);
        Log.d(PhotoActivable.DEBUG_TAG, msg);
    }

    private Entry<String, Integer>[] createEntries(Queue<Integer> els) {
        Date date = new Date();
        Entry<String, Integer>[] entries = new Entry[els.size()];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new SimpleEntry<>(createFileName(date, i), els.poll());
        }
        return entries;
    }

    private String createFileName(Date date, int index) {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        return String.format("IMG_%s_%s.jpg", dateFormat.format(date), index);
    }
}
