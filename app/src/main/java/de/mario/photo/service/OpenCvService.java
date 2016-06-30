package de.mario.photo.service;

import android.app.IntentService;

/**
 */
public abstract class OpenCvService extends IntentService {

    public static final String PARAM_PICS = "de.mario.camera.extra.PICS";

    public OpenCvService(String name) {
        super(name);
    }

}
