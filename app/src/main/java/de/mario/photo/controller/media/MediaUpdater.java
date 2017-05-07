package de.mario.photo.controller.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.FileObserver;
import android.os.Handler.Callback;
import android.os.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Send and broadcast to force update of media gallery
 */
final class MediaUpdater {
    private final Context context;
    private final List<Callback> callbacks;

    private File lastUpdated;
    private FileObserver fileObserver;


    MediaUpdater(Context context) {
        this.context = context;
        callbacks = new ArrayList<>();
    }

    void addCallback(Callback callback) {
        callbacks.add(callback);
    }

    void sendUpdate(File file) {
        lastUpdated = file;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
        monitorFile();
    }

    private void monitorFile() {
        if(fileObserver != null) {
            fileObserver.stopWatching();
        }
        fileObserver = new DeleteFileObserver(lastUpdated.getAbsolutePath());
        fileObserver.startWatching();
    }

    File getLastUpdated() {
        return lastUpdated;
    }

    private class DeleteFileObserver extends FileObserver {

        DeleteFileObserver(String path) {
            super(path, FileObserver.CREATE | FileObserver.DELETE_SELF | FileObserver.MOVED_FROM);
        }

        @Override
        public void onEvent(int event, String path) {
            Message msg = new Message();
            for (Callback callback : callbacks) {
                callback.handleMessage(msg);
            }
        }
    }
}
