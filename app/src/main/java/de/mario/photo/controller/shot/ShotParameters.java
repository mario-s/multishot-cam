package de.mario.photo.controller.shot;

import android.content.Context;
import android.os.Message;
import android.view.View;

import java.io.File;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.PhotoActivable;

/**
 */
class ShotParameters {

    private final PhotoActivable activity;
    private final ParameterUpdater updater;
    private Shot[] shots;
    private CameraControlable cameraController;
    private boolean trace;

    private boolean extraFlash;

    ShotParameters(CameraControlable cameraController, PhotoActivable activity, ParameterUpdater updater) {
        this.cameraController = cameraController;
        this.activity = activity;
        this.updater = updater;
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
        return activity.getContext().getString(key);
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
        boolean flash = false;
        if(extraFlash) {
            flash = shots[index].isFlash();
        }
        return flash;
    }

    void send(Message message) {
        cameraController.send(message);
    }

    void setExtraFlash(boolean extraFlash) {
        this.extraFlash = extraFlash;
    }
}
