package de.mario.camera.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import de.mario.camera.R;

/**
 *
 */
class NotificationSender {

    private Context context;

    private NotificationManager nManager;

    NotificationSender(Context context) {
        this.context = context;
        nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    void send(String text){
        NotificationCompat.Builder builder = createBuilder(text);
        nManager.notify(12345, builder.build());
    }

    private NotificationCompat.Builder createBuilder(String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Image Process")
                        .setContentText(text);
        return builder;
    }
}
