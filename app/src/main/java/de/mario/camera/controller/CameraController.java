package de.mario.camera.controller;

import android.hardware.Camera;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;
import de.mario.camera.SettingsAccess;
import de.mario.camera.controller.lookup.CameraLookup;
import de.mario.camera.controller.lookup.StorageLookable;
import de.mario.camera.controller.preview.FocusView;
import de.mario.camera.controller.preview.Preview;
import de.mario.camera.controller.shot.PhotoCommand;
import de.mario.camera.controller.support.IsoSupport;
import de.mario.camera.controller.support.PicturesSizeSupport;

/**
 */
public class CameraController implements CameraControlable{
    private static final int MIN = 0;

    private StorageLookable storageLookable;

    private PhotoActivable activity;
    private int camId = CameraLookup.NO_CAM_ID;
    private boolean canDisableShutterSound;

    private Camera camera;
    private Preview preview;
    private FocusView focusView;

    private IsoSupport isoSupport;
    private PicturesSizeSupport sizeSupport;
    private ScheduledExecutorService executor;
    private CameraOrientationListener orientationListener;
    private CameraLookup cameraLookup;
    private CameraFactory cameraFactory;
    private MessageSender messageSender;

    public CameraController() {
        this(null, new CameraLookup(), new CameraFactory());
    }

    CameraController(PhotoActivable activity, CameraLookup cameraLookup, CameraFactory cameraFactory) {
        this.activity = activity;
        this.cameraLookup = cameraLookup;
        this.cameraFactory = cameraFactory;
        executor = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public void setActivity(PhotoActivable activity) {
        this.activity = activity;
        messageSender = new MessageSender(activity.getHandler());
    }

    /**
     * Look for a camera and return true if we got one.
     * @return boolean
     */
    @Override
    public boolean lookupCamera() {
        camId = cameraLookup.findBackCamera();
        if (camId != CameraLookup.NO_CAM_ID) {
            canDisableShutterSound = cameraLookup.canDisableShutterSound(camId);
            return true;
        }
        return false;
    }

    /**
     * Initialize the camera. Make sure that you called lookup before and got a true value.
     */
    @Override
    public void initialize() {
        camera = cameraFactory.getCamera(camId);
        sizeSupport = new PicturesSizeSupport(camera.getParameters());
        isoSupport = new IsoSupport(camera.getParameters());

        orientationListener = new CameraOrientationListener(activity.getContext());
        if (orientationListener.canDetectOrientation()) {
            orientationListener.setCamera(camera);
            orientationListener.enable();
        }

        createViews();
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

    @Override
    public void shot() {
        enableShutterSound(!isShutterSoundDisabled());

        if(existsPictureSaveDirectory()) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                focusView.focused(success);
                if (success) {
                    execute(getSettings().getDelay());
                } else {
                    prepareNextShot();
                }
                }
            });
        }else{
            toast(activity.getResource(R.string.no_directory));
        }
    }

    private boolean isShutterSoundDisabled() { return getSettings().isShutterSoundDisabled();}

    private SettingsAccess getSettings() {
        return activity.getSettingsAccess();
    }

    private void execute(int delay) {
        Log.d(PhotoActivable.DEBUG_TAG, "delay for photo: " + delay);

        Runnable command = new PhotoCommand(CameraController.this, activity);

        if (delay > MIN) {
            executor.schedule(command, delay, TimeUnit.SECONDS);
        } else {
            executor.execute(command);
        }
    }

    private boolean existsPictureSaveDirectory(){
        File folder = getPictureSaveDirectory();
        return folder != null && folder.exists();
    }

    @Override
    public void toast(String msg) {
        messageSender.toast(msg);
        Log.d(PhotoActivable.DEBUG_TAG, msg);
    }

    private void prepareNextShot() {
        focusView.resetFocus();
        activity.prepareForNextShot();
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
    public Preview getPreview() {
        return preview;
    }

    @Override
    public FocusView getFocusView() {
        return focusView;
    }

    @Override
    public String[] getIsoValues() {
        return isoSupport.getIsoValues();
    }

    @Override
    public String getSelectedIsoValue(String isoKey) {
        return isoSupport.getSelectedIsoValue(isoKey);
    }

    @Override
    public String[] getSupportedPicturesSizes(){
        return sizeSupport.getSupportedPicturesSizes();
    }

    @Override
    public String getSelectedPictureSize() {
        return sizeSupport.getSelectedPictureSize(camera);
    }

    @Override
    public String findIsoKey(){
        return isoSupport.findIsoKey();
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

}
