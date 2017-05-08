package de.mario.photo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Handler.Callback;
import android.os.Message;

import de.mario.photo.glue.PhotoActivable;

/**
 * Receiver for messages when the processing of an image is completed,<br/>
 * or the file has been deleted by the user.
 */
final class ImageResultListener extends BroadcastReceiver implements Callback {

    private final PhotoActivity activity;

    ImageResultListener(PhotoActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PhotoActivable.EXPOSURE_MERGE)){
            String result = intent.getStringExtra(PhotoActivable.MERGED);
            activity.refreshPictureFolder(result);
            activity.toggleImageButton();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        activity.toggleImageButton();
        return true;
    }
}
