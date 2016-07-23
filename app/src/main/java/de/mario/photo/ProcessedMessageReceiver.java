package de.mario.photo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
final class ProcessedMessageReceiver extends BroadcastReceiver {

    private final PhotoActivity activity;

    ProcessedMessageReceiver(PhotoActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PhotoActivable.EXPOSURE_MERGE)){
            String result = intent.getStringExtra(PhotoActivable.MERGED);

            activity.hideProgress();
            activity.imageToast(result);
            activity.refreshPictureFolder(result);
        }
    }
}
