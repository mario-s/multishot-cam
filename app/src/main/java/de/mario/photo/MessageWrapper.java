package de.mario.photo;

import android.os.Message;

/**
 * This class wraps an android message.
 */
class MessageWrapper {
    private Message message;

    MessageWrapper(Message message){
        this.message = message;
    }

    boolean isDataEmpty() {
        return message.getData().isEmpty();
    }

    String getParcelAsString() {
        return message.obj.toString();
    }

    String getString(String key){
        return message.getData().getString(key);
    }

    String[] getStringArray(String key){
        return message.getData().getStringArray(key);
    }
}
