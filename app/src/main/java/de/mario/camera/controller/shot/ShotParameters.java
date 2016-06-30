package de.mario.camera.controller.shot;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import java.io.File;

import de.mario.camera.PhotoActivable;
import de.mario.camera.controller.CameraControlable;

/**
 */
class ShotParameters {

    private Shot[] shots;

    private CameraControlable cameraController;

    private final PhotoActivable activity;

    private final ParameterUpdater updater;

    private boolean trace;

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


    Handler getHandler(){
        return activity.getHandler();
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

    void toast(String message) {
        cameraController.toast(message);
    }
}
