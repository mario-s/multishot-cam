package de.mario.photo.controller.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.FileObserver;

import java.io.File;

/**
 * Send and broadcast to force update of media gallery
 */
final class MediaUpdater {
    private final Context context;

    private File lastUpdated;
    private FileObserver fileObserver;


    MediaUpdater(Context context) {
        this.context = context;
    }

    void sendUpdate(File file) {
        lastUpdated = file;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
    }

    File getLastUpdated() {
        return lastUpdated;
    }
}
