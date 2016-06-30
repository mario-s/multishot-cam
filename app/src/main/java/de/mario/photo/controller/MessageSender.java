package de.mario.photo.controller;

import android.os.Handler;
import android.os.Message;

/**
 * This class send messages from any client to registered {@link Handler}.
 */
public class MessageSender {

    private final Handler handler;

    public MessageSender(Handler handler) {
        this.handler = handler;
    }

    public void send(final Message message) {
        handler.sendMessage(message);
    }

    public void send(final String message) {
        send(createMessage(message));
    }

    Message createMessage(String message) {
        Message msg = Message.obtain();
        msg.obj = message;
        return msg;
    }
}
