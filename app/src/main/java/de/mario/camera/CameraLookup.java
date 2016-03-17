package de.mario.camera;

import android.hardware.Camera;
import android.support.annotation.NonNull;

/**
 * This class helps to look for available Camera on the device.
 */
class CameraLookup {
    private static final int MIN = 0;

    static final int NO_CAM_ID = -1;

    private int numberOfCameras;

    CameraLookup() {
        numberOfCameras = Camera.getNumberOfCameras();
    }

    /**
     * Search for the back facing camera.
     * @return id of the camera as int.
     */
    int findBackCamera() {
        int cameraId = NO_CAM_ID;

        for (int i = MIN; i < numberOfCameras; i++) {
            if (getCameraInfo(i).facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @NonNull
    private Camera.CameraInfo getCameraInfo(int id) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(id, info);
        return info;
    }
}