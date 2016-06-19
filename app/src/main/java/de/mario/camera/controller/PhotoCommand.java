package de.mario.camera.controller;

import android.hardware.Camera;
import android.os.Debug;
import android.util.Log;

import de.mario.camera.PhotoActivable;
import de.mario.camera.R;
import de.mario.camera.SettingsAccess;


/**
 * Command which will be executed to take pictures.
 */
class PhotoCommand implements Runnable{

    private final PhotoActivable activity;
    private final Camera camera;

    private ParameterUpdater updater;
    private final ShotParams shotParams;
    private final SettingsAccess settings;
    private PhotoShotsFactory photoShotsFactory;

    PhotoCommand(CameraController cameraController, PhotoActivable activity){
        this.activity = activity;
        this.camera = cameraController.getCamera();
        this.photoShotsFactory = new PhotoShotsFactory(camera);
        this.updater = new ParameterUpdater(camera);

        this.shotParams = new ShotParams(cameraController.getPreview(), activity, updater);
        this.shotParams.setTrace(activity.getSettingsAccess().isTrace());
        this.settings = activity.getSettingsAccess();
    }

    @Override
    public void run() {
        if (!activity.getPicturesDirectory().exists()) {
            toast(activity.getResource(R.string.no_directory));
            return;
        }

        prepareShots();
        if(shotParams.isTrace()) {
            Debug.startMethodTracing("multishot");
        }
        ContinuesCallback callback = new ContinuesCallback(shotParams);
        camera.takePicture(new DefaultShutterCallback(), new DefaultPictureCallback(), callback);
    }

    private void prepareShots() {

        updater.setPictureSize(settings.getPicSizeKey());

        Shot[] shots = photoShotsFactory.create(settings);
        //prepare the camera for the first exposure value
        if(shots.length > 0) {
            updater.setExposureCompensation(shots[0].getExposure());
        }else{
            updater.reset();
        }

        updater.setIso(settings.getIsoKey(), settings.getIsoValue());

        updater.update(camera);

        shotParams.setShots(shots);
    }

    private void toast(String msg) {
        MessageSender sender = new MessageSender(activity.getHandler());
        sender.toast(msg);
        Log.d(PhotoActivable.DEBUG_TAG, msg);
    }

}
