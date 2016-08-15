package de.mario.photo.controller;

/**
 * This interfaces exposes the controller to for the HDR process.
 */
public interface HdrProcessControlable {
    /**
     * Start the hdr process for the given images.
     *
     * @param pictures the complete paths of the images
     */
    void process(String[] pictures);

}
