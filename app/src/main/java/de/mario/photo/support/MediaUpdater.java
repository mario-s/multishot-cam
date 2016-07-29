package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.inject.Inject;

import java.io.File;

/**
 * Send and broadcast to force update of media gallery
 */
final public class MediaUpdater {
    private final Context context;
    private File lastUpdated;

    @Inject
    MediaUpdater(Context context) {
        this.context = context;
    }

    public void sendUpdate(File file) {
        lastUpdated = file;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
    }

    public File getLastUpdated() {
        return lastUpdated;
    }
}