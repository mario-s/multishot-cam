package de.mario.photo.controller.shot;

import android.content.Context;
import android.os.Message;
import android.view.View;

import java.io.File;

import de.mario.photo.PhotoActivable;
import de.mario.photo.controller.CameraControlable;
import de.mario.photo.controller.MessageSender;

/**
 */
class ShotParameters {

    private Shot[] shots;

    private CameraControlable cameraController;

    private final PhotoActivable activity;

    private final ParameterUpdater updater;

    private final MessageSender sender;

    private boolean trace;

    ShotParameters(CameraControlable cameraController, PhotoActivable activity, ParameterUpdater updater) {
        this.cameraController = cameraController;
        this.activity = activity;
        this.updater = updater;
        this.sender = new MessageSender(activity.getHandler());
    }

    void setShots(Shot[] shots) {
        this.shots = shots;
    }

    String [] getNames(){
        String[] names = new String[shots.length];
        for (int i = 0; i < names.length; i++){
            names[i] = shots[i].getName();
        }
        return names;
    }

    int [] getExposures() {
        int[] exposures = new int[shots.length];
        for (int i = 0; i < exposures.length; i++){
            exposures[i] = shots[i].getExposure();
        }
        return exposures;
    }

    Context getContext() {
        return (Context) activity;
    }

    /**
     * Return the image path on external storage.
     * @return path as string
     */
    File getPictureFileDir() {
        return cameraController.getPictureSaveDirectory();
    }

    String getResource(int key) {
        return activity.getResource(key);
    }

    View getPreview() {return cameraController.getPreview();}

    ParameterUpdater getUpdater() { return updater; }

    boolean isTrace() {
        return trace;
    }

    void setTrace(boolean trace) {
        this.trace = trace;
    }

    boolean isFlash(int index){
        return shots[index].isFlash();
    }


    void sendMessage(Message message) {
        sender.send(message);
    }

    void sendMessage(String message){
        sender.send(message);
    }
}
