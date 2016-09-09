package de.mario.photo.support;

import android.os.Build;

/**
 * This class checks the support for the Camera API
 */
public final class ApiSupport {
    private ApiSupport() {
    }

    public static boolean isApi2Supported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
