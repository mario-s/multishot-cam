package de.mario.photo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.inject.Inject;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

/**
 * @author Mario
 */
class StartupDialog {
    private static final String VERS = "version";
    private static final String PREFS = "PREFERENCE";

    private final Context context;

    @Inject
    StartupDialog(Context context) {
        this.context = context;
    }


    void showIfFirstRun() {
        String current = getCurrentVersion();
        if (isFirstRun(current)) {
            storeVersion(current);

            AlertDialog.Builder builder = newBuilder();
            builder.setMessage(context.getString(R.string.startup_text));
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, context, new Callback());
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    private boolean isFirstRun(String current) {
        String stored = getStoredVersion();
        return !stored.equals(current);
    }

    private void storeVersion(String current) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(VERS, current).apply();
    }

    private String getCurrentVersion() {
        return BuildConfig.VERSION_NAME + BuildConfig.VERSION_CODE;
    }

    private String getStoredVersion() {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(VERS, "");
    }

    private AlertDialog.Builder newBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        return builder;
    }

    private class Callback extends BaseLoaderCallback {

        Callback(){
            super(context);
        }

        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            //showIfFirstRun a message that we found a manager
            if(status == LoaderCallbackInterface.SUCCESS){

                AlertDialog.Builder builder = newBuilder();
                builder.setMessage(context.getString(R.string.open_cv_present));
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        }
    }
}
