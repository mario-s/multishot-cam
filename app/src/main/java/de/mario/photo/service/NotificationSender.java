package de.mario.photo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;

import de.mario.photo.R;

/**
 *
 */
class NotificationSender {

    private final String title;
    private Context context;
    private NotificationManager nManager;

    NotificationSender(Context context) {
        this.context = context;
        this.title = context.getString(R.string.title_merge_process);
        nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    void send(String text){
        NotificationCompat.Builder builder = createBuilder(text);

        nManager.notify(12345, addIntent(builder).build());
    }

    private NotificationCompat.Builder addIntent(NotificationCompat.Builder builder) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return builder.setContentIntent(pendingIntent);
    }

    private NotificationCompat.Builder createBuilder(String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.camera_burst_white_36dp)
                        .setContentTitle(title)
                        .setContentText(text);

        return builder;
    }
}
