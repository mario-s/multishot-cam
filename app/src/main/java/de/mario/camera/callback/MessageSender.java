package de.mario.camera.callback;

import android.os.Message;

import de.mario.camera.PhotoActivable;

/**
 *
 */
class MessageSender {

    private final PhotoActivable activity;

    MessageSender(PhotoActivable activity) {
        this.activity = activity;
    }

    void toast(final String message) {
        Message msg = createMessage(message);
        activity.getHandler().sendMessage(msg);
    }

    Message createMessage(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        return msg;
    }
}
