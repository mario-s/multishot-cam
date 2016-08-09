package de.mario.photo.support;

import android.os.Message;

/**
 * This class wraps an android message.
 */
public class MessageWrapper {
    private Message message;

    public MessageWrapper(Message message) {
        this.message = message;
    }

    public boolean isDataEmpty() {
        return message.getData().isEmpty();
    }

    public String getParcelAsString() {
        return message.obj.toString();
    }

    public String getString(String key) {
        return message.getData().getString(key);
    }

    public String[] getStringArray(String key) {
        return message.getData().getStringArray(key);
    }
}
