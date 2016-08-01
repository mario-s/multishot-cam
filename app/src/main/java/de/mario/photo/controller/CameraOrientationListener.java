package de.mario.photo.controller;

import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;

import de.mario.photo.AbstractOrientationListener;

/**
 * Listener when the orientation of the device changes, since we can't count
 * on surfaceChanged in Preview.
 */
class CameraOrientationListener extends AbstractOrientationListener{
    private Camera camera;

    CameraOrientationListener(Context context) {
        super(context);
    }

    void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    protected void orientationChanged(int orientation) {
        if(camera != null) {
            Camera.Parameters cameraParams = camera.getParameters();
            cameraParams.setRotation(orientation);
            camera.setParameters(cameraParams);
        }
    }


    /**
     * Returns orientation of the device for the given angle as an int value from 0 to 3.
     * Surface.ROTATION_90 for an angle greater 315 or smaller 45,
     * Surface.ROTATION_180 for an angle between 45 and 135,
     * Surface.ROTATION_270 for an angle between 135 and 225,
     * Surface.ROTATION_0 for an angle between 225 and 315,
     * otherwise 0.
     *
     * {@link Surface}
     *
     * @param angle the current angle
     * @return a orientation
     */
    @Override
    protected int getOrientationAbs(int angle) {
        int orientation = 0;
        if (angle >= 315 || angle < 45){
            orientation = Surface.ROTATION_90;
        }else if (angle >= 45 && angle < 135){
            orientation = Surface.ROTATION_180;
        }else if (angle >= 135 && angle < 225){
            orientation = Surface.ROTATION_270;
        }else if (angle >= 225 && angle < 315){
            orientation = Surface.ROTATION_0;
        }
        return orientation;
    }
}
