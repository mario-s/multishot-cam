package de.mario.photo.view;

/**
 * Noise reduction for the values of the device orientation
 */

class OrientationNoiseFilter {

    private static final float UPDATE_RATE = .3f;

    private static final int MAX = 360;

    int filter(int newValue, int oldValue) {
        //upright slightly from left to right
        if(inLast(oldValue) && inFirst(newValue)){
            return norm(calc(newValue + MAX, oldValue));
        }
        //upright slightly from right to left
        if(inFirst(oldValue) && inLast(newValue)) {
            return norm(calc(newValue, oldValue + MAX));
        }

        return calc(newValue, oldValue);
    }

    private boolean inFirst(int val) {
        return val >= 0 && val <= 90;
    }

    private boolean inLast(int val) {
        return val >= 270 && val < MAX;
    }

    private int norm(int val) {
        return (val >= MAX) ? val - MAX : val;
    }

    private int calc(int newValue, int oldValue) {
        return (int)(oldValue * (1f - UPDATE_RATE) + newValue * UPDATE_RATE);
    }
}
