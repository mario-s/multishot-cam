package de.mario.camera.callback;

import android.hardware.Camera;
import android.util.Log;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;

/**
 * Command which will be executed to take pictures.
 */
public class PhotoCommand implements Runnable{

    private final PhotoActivable activity;
    private final Camera camera;

    public PhotoCommand(PhotoActivable activity, Camera camera){
        this.activity = activity;
        this.camera = camera;
    }

    @Override
    public void run() {
        if (!activity.getPicturesDirectory().exists()) {
            toast(activity.getResource(R.string.no_directory));
            return;
        }

        ContinuesCallback callback = new ContinuesCallback(activity);
        camera.takePicture(new ShutterCallback(), new LoggingPictureCallback(), callback);
    }

    private void toast(String msg) {
        MessageSender sender = new MessageSender(activity);
        sender.toast(msg);
        Log.d(PhotoActivable.DEBUG_TAG, msg);
    }
}
