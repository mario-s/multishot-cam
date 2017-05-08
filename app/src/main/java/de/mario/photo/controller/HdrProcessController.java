package de.mario.photo.controller;

import android.content.Context;
import android.content.Intent;

import org.opencv.android.OpenCVLoader;

import de.mario.photo.service.ExposureMergeService;
import de.mario.photo.service.OpenCvService;

/**
 * Controller to start the HDR process.
 */
class HdrProcessController implements HdrProcessControlable {
    private final Context context;

    HdrProcessController(Context context) {
        this.context = context;
    }

    @Override
    public void process(String[] pictures) {
        Intent intent = new Intent(context, ExposureMergeService.class);
        intent.putExtra(OpenCvService.PARAM_PICS, pictures);
        OpenCvLoaderCallback callback = new OpenCvLoaderCallback(context, intent);
        callLoader(callback);
    }

    void callLoader(OpenCvLoaderCallback callback) {
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, context, callback);
    }

}
