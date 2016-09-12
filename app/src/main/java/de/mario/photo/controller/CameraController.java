package de.mario.photo.controller;

import android.hardware.Camera;
import android.os.Message;

import java.io.File;

import de.mario.photo.R;
import de.mario.photo.controller.lookup.CameraLookup;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.controller.shot.PhotoCommand;
import de.mario.photo.glue.CameraProvideable;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.support.MessageWrapper;
import de.mario.photo.view.FocusView;
import de.mario.photo.view.Preview;
import roboguice.util.Ln;

/**
 */
public class CameraController extends AbstractCameraController implements CameraProvideable {
    private static final int MIN = 0;

    private StorageLookable storageLookable;

    private int camId = CameraLookup.NO_CAM_ID;
    private boolean canDisableShutterSound;

    private Camera camera;
    private Preview preview;
    private FocusView focusView;


    private CameraOrientationListener orientationListener;
    private CameraLookup cameraLookup;
    private CameraFactory cameraFactory;

    public CameraController() {
        this(new CameraLookup(), new CameraFactory());
    }

    CameraController(CameraLookup cameraLookup, CameraFactory cameraFactory) {
        this.cameraLookup = cameraLookup;
        this.cameraFactory = cameraFactory;

    }

    @Override
    public boolean lookupCamera() {
        camId = cameraLookup.findBackCamera();
        if (camId != CameraLookup.NO_CAM_ID) {
            canDisableShutterSound = cameraLookup.canDisableShutterSound(camId);
            return true;
        }
        return false;
    }

    @Override
    public void initialize() {
        camera = cameraFactory.getCamera(camId);

        createViews();

        handler.post(new Runnable() {
            @Override
            public void run() {

                orientationListener = new CameraOrientationListener(activity.getContext());
                if (orientationListener.canDetectOrientation()) {
                    orientationListener.setCamera(camera);
                    orientationListener.enable();
                }
            }
        });
    }

    private void createViews() {
        if(preview == null){
            preview = new Preview(activity.getContext(), camera);
        }
        if(focusView == null) {
            focusView = new FocusView(activity.getContext());
        }
    }

    @Override
    public void reinitialize(){
        if(camera == null) {
            initialize();
        }
    }

    @Override
    public void releaseCamera() {
        preview = null;
        orientationListener.disable();
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    private boolean isShutterSoundDisabled() {
        return getSettingsAccess().isEnabled(R.string.shutterSoundDisabled);
    }

    private void execute(int delay) {
        Ln.d("delay for photo: %s", delay);

        Runnable command = new PhotoCommand(CameraController.this, activity);
        if (delay > MIN) {
            handler.postDelayed(command, delay * 1000);
        } else {
            handler.post(command);
        }
    }

    private boolean existsPictureSaveDirectory(){
        File folder = getPictureSaveDirectory();
        return folder != null && folder.exists();
    }

    private void enableShutterSound(boolean enable) {
        if(canDisableShutterSound) {
            camera.enableShutterSound(enable);
        }
    }

    void setStorageLookup(StorageLookable storageLookup) {
        this.storageLookable = storageLookup;
    }

    @Override
    public File getPictureSaveDirectory() {
        return storageLookable.lookupSaveDirectory();
    }

    @Override
    public void send(Message message) {
        MessageWrapper wrapper = new MessageWrapper(message);
        if (!wrapper.isDataEmpty() && wrapper.getStringArray(PhotoActivable.PICTURES) != null) {
            focusView.resetFocus();
        }
        super.send(message);
    }

    @Override
    public Preview getPreview() {
        return preview;
    }

    @Override
    public FocusView getFocusView() {
        return focusView;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void shot() {
        handler.post(new ShotRunner());
    }

    class ShotRunner implements Runnable{
        @Override
        public void run() {
            enableShutterSound(!isShutterSoundDisabled());

            if(existsPictureSaveDirectory()) {
                camera.autoFocus(new FocusCallBack());
            }else{
                send(activity.getContext().getString(R.string.no_directory));
            }
        }
    }

    class FocusCallBack implements Camera.AutoFocusCallback {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            focusView.focused(success);
            if (success) {
                execute(getSettingsAccess().getDelay());
            } else {
                send(PhotoActivable.PREPARE_FOR_NEXT);
            }
        }
    }
}
