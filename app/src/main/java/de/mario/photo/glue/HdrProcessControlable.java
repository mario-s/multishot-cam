package de.mario.photo.glue;

/**
 * This interfaces exposes the controller for the HDR process.
 */
public interface HdrProcessControlable {
    /**
     * Start the hdr process for the given images.
     *
     * @param pictures the complete paths of the images
     */
    void process(String[] pictures);

}
