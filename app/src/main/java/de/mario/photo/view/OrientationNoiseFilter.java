package de.mario.photo.view;

import static java.lang.Math.*;

/**
 * Noise reduction for the values of the device orientation.<br/>
 * It uses a low pass filter.
 */
class OrientationNoiseFilter {

    private static final float ALPHA = .2f;

    static final int MAX = 360;

    private int values[] = new int[0];

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

        return filtered;
    }

    private int filter(int previous, int current) {
        //convert to radians
        double radPrev = toRadians(previous);
        double radCurrent = toRadians(current);
        //filter based on sin & cos
        double sumSin = filter(sin(radPrev), sin(radCurrent));
        double sumCos = filter(cos(radPrev), cos(radCurrent));
        //calculate result angle
        double radRes = atan(sumSin/sumCos);
        //convert radians to degree, round it and normalize (modulo of 360)
        return (int) ((MAX + round(toDegrees(radRes))) % MAX);
    }

    private double filter(double previous, double current) {
        return (previous + ALPHA * (current - previous));
    }
}
