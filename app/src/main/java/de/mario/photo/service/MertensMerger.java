package de.mario.photo.service;

import org.opencv.core.Mat;
import org.opencv.photo.MergeMertens;
import org.opencv.photo.Photo;

import java.util.List;

/**
 * Mertens merge.
 */
class MertensMerger implements Merger {

    @Override
    public Mat merge(List<Mat> images) {

        Mat fusion = new Mat();
        MergeMertens mergeMertens = Photo.createMergeMertens();
        mergeMertens.process(images, fusion);

        return fusion;
    }
}
