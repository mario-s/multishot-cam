package de.mario.photo.controller;

import android.os.Handler;
import android.os.Message;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.glue.SettingsAccessable;
import de.mario.photo.support.HandlerThreadFactory;
import roboguice.util.Ln;

/**
 *
 */
public abstract class AbstractCameraController implements CameraControlable {

    protected Handler handler;
    protected PhotoActivable activity;
    private SettingsAccessable settingsAccess;
    private MessageSender messageSender;

    public AbstractCameraController() {
        HandlerThreadFactory factory = new HandlerThreadFactory(getClass());
        handler = factory.newHandler();
    }

    @Override
    public SettingsAccessable getSettingsAccess() {
        return settingsAccess;
    }

    void setSettingsAccess(SettingsAccessable settingsAccess) {
        this.settingsAccess = settingsAccess;
    }

    @Override
    public void setActivity(PhotoActivable activity) {
        this.activity = activity;
        messageSender = new MessageSender(activity.getHandler());
    }

    protected void send(String msg) {
        messageSender.send(msg);
        Ln.d("sending message: %s", msg);
    }

    public void send(Message message) {
        messageSender.send(message);
    }
}
