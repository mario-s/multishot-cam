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

}
