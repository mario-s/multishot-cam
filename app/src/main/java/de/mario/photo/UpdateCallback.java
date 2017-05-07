package de.mario.photo;

import android.os.Handler.Callback;
import android.os.Message;

/**
 */
class UpdateCallback implements Callback {

    private PhotoActivity activity;

    UpdateCallback(PhotoActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean handleMessage(Message msg) {
        activity.toggleImageButton();
        return true;
    }
}
