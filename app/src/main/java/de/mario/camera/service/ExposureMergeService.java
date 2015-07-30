package de.mario.camera.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.MergeMertens;
import org.opencv.photo.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ExposureMergeService extends IntentService {

    static final String PARAM_PICS = "de.mario.camera.extra.PICS";


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startProcessing(Context context, String [] pictures) {
        Intent intent = new Intent(context, ExposureMergeService.class);
        intent.putExtra(PARAM_PICS, pictures);
        context.startService(intent);
    }

    public ExposureMergeService() {
        super("ExposureMergeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleProcessing(intent.getStringArrayExtra(PARAM_PICS));
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleProcessing(String [] pictures) {

        List<Mat> images = loadImages(pictures);

        Mat fusion = new Mat();
        MergeMertens mergeMertens = Photo.createMergeMertens();
        mergeMertens.process(images, fusion);

        File out = new File(createFileName(pictures[0]));
        write(fusion, out);

    }

    private String createFileName(String src) {
        int pos = src.lastIndexOf(".") ;
        String preffix = src.substring(0, pos - 1);
        String suffix = src.substring(pos);
        return preffix + "merged" + suffix;
    }

    private void write(Mat fusion, File out) {
        Mat result = new Mat();
        Scalar scalar = new Scalar(255, 255, 255);
        Core.multiply(fusion, scalar, result);
        Imgcodecs.imwrite(out.getPath(), result);
    }

    private List<Mat> loadImages(String[] pics) {
        List<Mat> imgs = new ArrayList<>();

        for(String pic : pics){
            File f = new File(pic);
            Mat read = Imgcodecs.imread(f.getPath());
            imgs.add(read);
        }

        return imgs;
    }
}
