package de.mario.camera.preview;

import android.hardware.Camera;

/**
 * This is a pojo made for easier test of the preview.
 */
final class Dim {
    private int width;

    private int height;

    Dim(Camera.Size size) {
        this(size.width, size.height);
    }

    Dim(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        Dim dim = (Dim) o;

        if (width != dim.width) { return false;}
        return height == dim.height;

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
