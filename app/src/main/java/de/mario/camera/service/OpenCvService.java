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

    protected static final String PARAM_PICS = "de.mario.camera.extra.PICS";

    public OpenCvService(String name) {
        super(name);
    }

    public void startProcessing(Context context, String [] pictures) {
        Intent intent = new Intent(context, getClass());
        intent.putExtra(PARAM_PICS, pictures);

        OpenCvLoaderCallback callback = new OpenCvLoaderCallback(context, intent);
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, context, callback);
    }

    private class OpenCvLoaderCallback extends BaseLoaderCallback {
        private final Context context;
        private final Intent intent;

        OpenCvLoaderCallback(Context context, Intent intent){
            super(context);
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onManagerConnected(int status) {
            if (status  == LoaderCallbackInterface.SUCCESS) {
                context.startService(intent);
            }else{
                super.onManagerConnected(status);
            }
        }
    }
}
