package de.mario.photo.glue;

import android.hardware.Camera;
import android.os.Message;
import android.view.View;

import java.io.File;

/**
 */
public interface CameraControlable {

    /**
     * Look for a camera and return true if we got one.
     * @return boolean
     */
    boolean lookupCamera();

    /**
     * Initialize the camera. Make sure that you called lookup before and got a true value.
     */
    void initialize();

    void reinitialize();

    void releaseCamera();

    void shot();

    View getPreview();

    View getFocusView();

    void setActivity(PhotoActivable activity);

    Camera getCamera();

    File getPictureSaveDirectory();

    void send(Message message);

}
