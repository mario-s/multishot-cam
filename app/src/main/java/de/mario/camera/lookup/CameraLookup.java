package de.mario.camera.lookup;

import android.hardware.Camera;

/**
 * This class helps to look for available Cameras and their features on the device.
 */
public class CameraLookup {
    private static final int MIN = 0;

    public static final int NO_CAM_ID = -1;

    private int numberOfCameras;

    public CameraLookup() {
        numberOfCameras = Camera.getNumberOfCameras();
    }

    /**
     * Search for the back facing camera.
     * @return id of the camera as int.
     */
    public int findBackCamera() {
        int cameraId = NO_CAM_ID;

        for (int i = MIN; i < numberOfCameras; i++) {
            if (getCameraInfo(i).facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public boolean canDisableShutterSound(int id) {
        return getCameraInfo(id).canDisableShutterSound;
    }

    private Camera.CameraInfo getCameraInfo(int id) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(id, info);
        return info;
    }
}
