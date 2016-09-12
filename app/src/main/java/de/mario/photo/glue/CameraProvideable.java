package de.mario.photo.glue;

import android.hardware.Camera;

/**
 * Supplies the camera based on the deprecated API
 */
public interface CameraProvideable extends CameraControlable {
    Camera getCamera();
}
