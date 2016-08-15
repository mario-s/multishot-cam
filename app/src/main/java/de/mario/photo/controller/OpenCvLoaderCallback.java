package de.mario.photo.controller;

import android.content.Context;
import android.content.Intent;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;

/**
 * Callback when OpenCv is loaded.
 */
public class OpenCvLoaderCallback extends BaseLoaderCallback {
    private final Intent intent;

    OpenCvLoaderCallback(Context context, Intent intent){
        super(context);
        this.intent = intent;
    }

    @Override
    public void onManagerConnected(int status) {
        if (status  == LoaderCallbackInterface.SUCCESS) {
            mAppContext.startService(intent);
        }else{
            super.onManagerConnected(status);
        }
    }
}
