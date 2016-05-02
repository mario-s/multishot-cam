package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final ShotParams shotParams;

    public PhotoCommand(PhotoActivable activity, Camera camera){
        this.activity = activity;
        this.camera = camera;
        this.shotParams = new ShotParams(activity);
    }

    @Override
    public void run() {
        if (!activity.getPicturesDirectory().exists()) {
            toast(activity.getResource(R.string.no_directory));
            return;
        }

        Shot[] shots = createEntries(activity.getExposureValues());
        //prepare the camera for the first exposure value
        if(shots.length > 0) {
            setExposureCompensation(camera, shots[0].getExposure());
        }else{
            resetExposure(camera);
        }
        shotParams.setShots(shots);
        ContinuesCallback callback = new ContinuesCallback(shotParams);
        camera.takePicture(new DefaultShutterCallback(), new DefaultPictureCallback(), callback);
    }

    private void toast(String msg) {
        MessageSender sender = new MessageSender(activity.getHandler());
        sender.toast(msg);
        Log.d(PhotoActivable.DEBUG_TAG, msg);
    }

    private Shot[] createEntries(Queue<Integer> els) {
        Date date = new Date();
        Shot[] shots = new Shot[els.size()];
        for (int i = 0; i < shots.length; i++) {
            shots[i] = new Shot(createFileName(date, i), els.poll());
        }
        return shots;
    }

    private String createFileName(Date date, int index) {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        //TODO pattern according to DCF
        return String.format("DSC_%s_%s.jpg", dateFormat.format(date), index);
    }
}
