package de.mario.photo.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import de.mario.photo.glue.MediaUpdateable;

/**
 * Send and broadcast to force update of media gallery
 */
final public class MediaUpdater implements MediaUpdateable {
    private final Context context;
    private File lastUpdated;

    MediaUpdater(Context context) {
        this.context = context;
    }

    @Override
    public void sendUpdate(File file) {
        lastUpdated = file;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
    }

    @Override
    public File getLastUpdated() {
        return lastUpdated;
    }
}
