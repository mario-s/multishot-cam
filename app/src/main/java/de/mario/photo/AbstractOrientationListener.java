package de.mario.photo;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.Surface;

/**
 * This class fires an event only if the device is in one of the angles:  0, 90, 180 or 270.
 */
public abstract class AbstractOrientationListener extends OrientationEventListener{
    private int lastOrientation = -1;

    public AbstractOrientationListener(Context context) {
        super(context);
    }

    protected void setLastOrientation(int lastOrientation) {
        this.lastOrientation = lastOrientation;
    }

    protected int getLastOrientation() {
        return lastOrientation;
    }

    @Override
    public void onOrientationChanged(int angle) {
        int orientation = getOrientationInDeg(angle);
        if(orientation != getLastOrientation()) {
            orientationChanged(orientation);
            setLastOrientation(orientation);
        }
    }

    protected abstract void orientationChanged(int orientation);

    /**
     * Returns orientation of the device for the given angle as an int value.
     * Possible values: 0, 90, 180 or 270.
     * @param angle
     * @return
     */
    protected int getOrientationInDeg(int angle){
        int angleAbs = getOrientationAbs(angle);
        if(angleAbs == 1) {
            return 90;
        }
        if(angleAbs == 2) {
            return 180;
        }
        if(angleAbs == 3){
            return 270;
        }
        return 0;
    }


    /**
     * Returns orientation of the device for the given angle as an int value from 0 to 3.
     * Surface.ROTATION_0 for an angle greater 315 or smaller 45,
     * Surface.ROTATION_90 for an angle between 45 and 135,
     * Surface.ROTATION_180 for an angle between 135 and 225,
     * Surface.ROTATION_270 for an angle between 225 and 315,
     * otherwise 0.
     *
     * {@link Surface}
     *
     * @param angle the current angle
     * @return a orientation
     */
    protected int getOrientationAbs(int angle) {
        int orientation = 0;
        if (angle >= 315 || angle < 45){
            orientation = Surface.ROTATION_0;
        }else if (angle >= 45 && angle < 135){
            orientation = Surface.ROTATION_90;
        }else if (angle >= 135 && angle < 225){
            orientation = Surface.ROTATION_180;
        }else if (angle >= 225 && angle < 315){
            orientation = Surface.ROTATION_270;
        }
        return orientation;
    }
}
