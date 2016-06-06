package de.mario.camera.controller;

import android.content.Context;
import android.hardware.Camera;
import android.view.OrientationEventListener;
import android.view.Surface;

/**
 * Listener when the orientation of the device changes, since we can't count
 * on surfaceChanged in Preview.
 */
class OrientationListener extends OrientationEventListener{
    private Camera camera;
    private int lastOrientation = -1;

    OrientationListener(Context context) {
        super(context);
    }

    void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void onOrientationChanged(int angle) {
        if(camera != null) {
            int orientation = getOrientation(angle);
            if(orientation != lastOrientation) {
                Camera.Parameters cameraParams = camera.getParameters();

                if (orientation == 1 || orientation == 3) { //vertical
                    cameraParams.setRotation(90);
                } else if (orientation == 0 || orientation == 2) {
                    cameraParams.setRotation(0);
                }

                camera.setParameters(cameraParams);
                lastOrientation = orientation;
            }
        }
    }

    private int getOrientation(int angle) {
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
