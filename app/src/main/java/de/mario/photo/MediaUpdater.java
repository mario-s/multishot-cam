package de.mario.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.inject.Inject;

import java.io.File;

/**
 * Send and broadcast to force update of media gallery
 */
final class MediaUpdater {
    private final Context context;
    private Uri lastUpdated;

    @Inject
    MediaUpdater(Context context) {
        this.context = context;
    }

    void sendUpdate(File file) {
        lastUpdated = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, lastUpdated);
        context.sendBroadcast(intent);
    }

    Uri getLastUpdated() {
        return lastUpdated;
    }
}
