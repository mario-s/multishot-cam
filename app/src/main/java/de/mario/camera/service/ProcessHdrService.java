package de.mario.camera.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ProcessHdrService extends IntentService {

    private static final String PARAM_PICS = "de.mario.camera.extra.PICS";

    private final ExposureTimeReader expTimeReader;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startProcessing(Context context, String [] pictures) {
        Intent intent = new Intent(context, ProcessHdrService.class);
        intent.putExtra(PARAM_PICS, pictures);
        context.startService(intent);
    }

    public ProcessHdrService() {
        super("ProcessHdrService");
        expTimeReader = new ExposureTimeReader();
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
        Map<String, Double> exposures = expTimeReader.readExposureTimes(pictures);

//        throw new UnsupportedOperationException("Not yet implemented");
    }

}
