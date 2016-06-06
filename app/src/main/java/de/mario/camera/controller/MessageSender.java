package de.mario.camera.controller;

import android.os.Handler;
import android.os.Message;

/**
 *
 */
class MessageSender {

    private final Handler handler;

    MessageSender(Handler handler) {
        this.handler = handler;
    }

    void toast(final String message) {
        Message msg = createMessage(message);
        handler.sendMessage(msg);
    }

    Message createMessage(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        return msg;
    }
}
