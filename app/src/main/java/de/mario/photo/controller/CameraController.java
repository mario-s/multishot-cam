package de.mario.photo.controller;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

import java.io.File;

import de.mario.photo.PhotoActivable;
import de.mario.photo.R;
import de.mario.photo.controller.lookup.CameraLookup;
import de.mario.photo.controller.lookup.StorageLookable;
import de.mario.photo.controller.shot.PhotoCommand;
import de.mario.photo.settings.SettingsAccess;
import de.mario.photo.support.HandlerThreadFactory;
import de.mario.photo.support.IsoSupport;
import de.mario.photo.support.MessageWrapper;
import de.mario.photo.support.PicturesSizeSupport;
import de.mario.photo.view.FocusView;
import de.mario.photo.view.Preview;
import roboguice.util.Ln;

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
    private CameraOrientationListener orientationListener;
    private CameraLookup cameraLookup;
    private CameraFactory cameraFactory;
    private MessageSender messageSender;
    private SettingsAccess settingsAccess;

    private Handler handler;

    public CameraController() {
        this(new CameraLookup(), new CameraFactory());
    }

    CameraController(CameraLookup cameraLookup, CameraFactory cameraFactory) {
        this.cameraLookup = cameraLookup;
        this.cameraFactory = cameraFactory;

        HandlerThreadFactory factory = new HandlerThreadFactory(getClass());
        handler = factory.newHandler();
    }

    @Override
    public void setActivity(PhotoActivable activity) {
        this.activity = activity;
        messageSender = new MessageSender(activity.getHandler());
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

                sizeSupport = new PicturesSizeSupport(camera.getParameters());
                isoSupport = new IsoSupport(camera.getParameters());

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

    private boolean isShutterSoundDisabled() { return getSettings().isEnabled(R.string.shutterSoundDisabled);}

    private SettingsAccess getSettings() {
        return settingsAccess;
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
        messageSender.send(message);
    }

    private void send(String msg) {
        messageSender.send(msg);
        Ln.d("sending message: %s", msg);
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

    @Override
    public void shot() {
        handler.post(new ShotRunner());
    }

    @Override
    public SettingsAccess getSettingsAccess() {
        return settingsAccess;
    }

    void setSettingsAccess(SettingsAccess settingsAccess) {
        this.settingsAccess = settingsAccess;
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
                execute(getSettings().getDelay());
            } else {
                send(PhotoActivable.PREPARE_FOR_NEXT);
            }
        }
    }
}
