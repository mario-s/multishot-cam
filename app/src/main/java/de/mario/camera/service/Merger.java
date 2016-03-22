package de.mario.camera.service;

import org.opencv.core.Mat;

import java.util.List;

/**
 * Interface to encapsulate the image merge process.
 */
public interface Merger {
    Mat merge(List<Mat> images);
}
