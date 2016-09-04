package de.mario.photo.controller.shot;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Debug;

import de.mario.photo.glue.CameraControlable;
import de.mario.photo.glue.PhotoActivable;
import de.mario.photo.settings.SettingsAccess;


/**
 * Command which will be executed to take pictures.
 */
public class PhotoCommand implements Runnable{

    private final PhotoActivable activity;
    private final Camera camera;
    private final ShotParameters shotParams;
    private final SettingsAccess settings;
    private ParameterUpdater parameterUpdater;
    private PhotoShotsFactory photoShotsFactory;

    public PhotoCommand(CameraControlable cameraController, PhotoActivable activity){
        this.activity = activity;
        this.camera = cameraController.getCamera();
        this.settings = cameraController.getSettingsAccess();

        this.photoShotsFactory = new PhotoShotsFactory(camera);
        this.parameterUpdater = new ParameterUpdater(camera);
        this.shotParams = new ShotParameters(cameraController, activity, parameterUpdater);
    }

    @Override
    public void run() {
        prepareShots();
        camera.takePicture(new DefaultShutterCallback(), new DefaultPictureCallback(), newPictureCallback());
    }

    PictureCallback newPictureCallback() {
        return new ContinuesPictureCallback(shotParams);
    }

    private void prepareShots() {

        parameterUpdater.setPictureSize(settings.getPicSizeKey());

        Shot[] shots = photoShotsFactory.create(settings);
        //prepare the camera for the first exposure value
        if(shots.length > 0) {
            parameterUpdater.setExposureCompensation(shots[0].getExposure());
        }else{
            parameterUpdater.reset();
        }

        parameterUpdater.setIso(settings.getIsoKey(), settings.getIsoValue());
        parameterUpdater.enableContinuesFlash(settings.isFlashForEach());
        parameterUpdater.update(camera);

        shotParams.setExtraFlash(settings.isLastFlash());
        shotParams.setShots(shots);

        boolean trace = settings.isTrace();
        shotParams.setTrace(trace);
        if(trace) {
            Debug.startMethodTracing("multishot");
        }
    }

}
