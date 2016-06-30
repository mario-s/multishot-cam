package de.mario.photo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

/**
 * @author Mario
 */
class StartupDialog {

    private final Context context;

    StartupDialog(Context context) {
        this.context = context;
    }

    void show() {
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
            //show a message that we found a manager
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
