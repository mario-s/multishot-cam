package de.mario.photo.view;

import roboguice.util.Ln;

import static java.lang.Math.*;

/**
 * Noise reduction for the values of the device orientation.<br/>
 * It uses a low pass filter.
 */
class OrientationNoiseFilter {

    private static final float TIME_CONSTANT = .297f;

    static final int MAX = 360;

    private double alpha;

    private float timestamp;

    private float timestampOld;

    private int count;

    private int values[];

    OrientationNoiseFilter() {
        timestamp = System.nanoTime();
        timestampOld = System.nanoTime();
        values = new int[0];
    }

    /**
     * Filters noise out of the orientation values.
     * @param input values in degree
     * @return result in degree.
     */
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

        Ln.d("filtered values: %s, %s", values[0], values[1]);

        return filtered;
    }

    private int filter(int previous, int current) {
        calculateAlpha();
        //convert to radians
        double radPrev = toRadians(previous);
        double radCurrent = toRadians(current);
        //filter based on sin & cos
        double sumSin = filter(sin(radPrev), sin(radCurrent));
        double sumCos = filter(cos(radPrev), cos(radCurrent));
        //calculate result angle
        double radRes = atan2(sumSin, sumCos);
        //convert radians to degree, round it and normalize (modulo of 360)
        long round = round(toDegrees(radRes));
        return (int) ((MAX + round) % MAX);
    }

    private void calculateAlpha() {
        timestamp = System.nanoTime();
        // Find the sample period (between updates).
        // Convert from nanoseconds to seconds
        float diff = timestamp - timestampOld;
        double dt = 1 / (count / (diff / 1000000000.0f));
        count++;
        // Calculate alpha
        alpha = dt/(TIME_CONSTANT + dt);
    }

    private double filter(double previous, double current) {
        return (previous + alpha * (current - previous));
    }
}
