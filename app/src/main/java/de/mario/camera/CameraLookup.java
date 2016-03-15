package de.mario.camera;

import android.hardware.Camera;

/**
 * This class helps to look for available Camera on the device.
 */
class CameraLookup {
    private static final int MIN = 0;

    static final int NO_CAM_ID = -1;

    /**
     * Search for the back facing camera.
     * @return id of the camera as int.
     */
    int findBackCamera() {
        int cameraId = NO_CAM_ID;

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = MIN; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
}
