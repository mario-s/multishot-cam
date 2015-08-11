package de.mario.camera.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

/**
 */
public abstract class OpenCvService extends IntentService {

    public static final String PARAM_PICS = "de.mario.camera.extra.PICS";

    public OpenCvService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        OpenCvLoaderCallback callback = new OpenCvLoaderCallback(this, intent);
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, callback);
    }

    protected abstract void process(Intent intent);

    private class OpenCvLoaderCallback extends BaseLoaderCallback {
        private final Intent intent;

        OpenCvLoaderCallback(Context context, Intent intent){
            super(context);
            this.intent = intent;
        }

        @Override
        public void onManagerConnected(int status) {
            if (status  == LoaderCallbackInterface.SUCCESS) {
                process(intent);
            }else{
                super.onManagerConnected(status);
            }
        }
    }
}
