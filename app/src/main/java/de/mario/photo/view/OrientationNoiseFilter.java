package de.mario.photo.view;

/**
 * Noise reduction for the values of the device orientation.<br/>
 * It uses a low pass filter.
 */
class OrientationNoiseFilter {

    private static final float ALPHA = .3f;

    private static final int MAX = 360;

    private int values[] = new int[0];

    int filter(int input) {
        //there is no need to filter if we have only one
        if(values.length == 0) {
            values = new int[] {0, input};
            return input;
        }

        //filter based on last element from array and input
        int filtered = filter(values[1], input);
        //new array based on previous result and filter
        values = new int[] {values[1], filtered};

        return filtered;
    }

    private int filter(int previous, int current) {
        //upright slightly from left to right
        if(inLast(previous) && inFirst(current)){
            return calc(current + MAX, previous) % MAX;
        }
        //upright slightly from right to left
        if(inFirst(previous) && inLast(current)) {
            return calc(current, previous + MAX) % MAX;
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
