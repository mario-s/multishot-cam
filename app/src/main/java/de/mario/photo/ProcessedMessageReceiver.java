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
            activity.hideProgress();
            String result = intent.getStringExtra("merged");
            activity.toast(result);
        }
    }
}
