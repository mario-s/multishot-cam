package de.mario.photo.view;

/**
 * Noise reduction for the values of the device orientation
 */

class OrientationNoiseFilter {

    private static final float ALPHA = .3f;

    private static final int MAX = 360;

    int filter(int previous, int current) {
        //upright slightly from left to right
        if(inLast(previous) && inFirst(current)){
            return calc(current + MAX, previous) % MAX;
        }
        //upright slightly from right to left
        if(inFirst(previous) && inLast(current)) {
            return calc(current, previous + MAX) % 360;
        }

        return calc(current, previous);
    }

    private boolean inFirst(int val) {
        return val >= 0 && val <= 90;
    }

    private boolean inLast(int val) {
        return val >= 270 && val < MAX;
    }

    private int calc(int current, int previous) {
        return (int)(previous + ALPHA * (current - previous));
    }
}
