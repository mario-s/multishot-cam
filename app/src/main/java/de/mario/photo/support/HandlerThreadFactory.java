package de.mario.photo.support;

import android.os.Handler;
import android.os.HandlerThread;

/**
 *
 */
public class HandlerThreadFactory {
    private final Class clazz;

    public HandlerThreadFactory(Class clazz) {
        this.clazz = clazz;
    }

    public Handler newHandler() {
        HandlerThread handlerThread = new HandlerThread(clazz.getSimpleName());
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }
}
