package de.mario.camera;

import android.content.Context;

import java.util.Queue;

/**
 * Interface for coupling between the activity and sub classes.
 */
public interface PhotoActivable {

    String DEBUG_TAG = "PhotoActivity";

    Context getApplicationContext();

    Queue<Integer> getExposureValues();
}
