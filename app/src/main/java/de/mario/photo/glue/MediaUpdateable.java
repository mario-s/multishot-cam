package de.mario.photo.glue;

import java.io.File;

/**
 */

public interface MediaUpdateable {
    void sendUpdate(File file);

    File getLastUpdated();
}
