package de.mario.camera.callback;

/**
 * Created by Mario on 24.04.2016.
 */
final class Shot {
    private final String name;
    private final int exposure;

    Shot(String name, int exposure) {
        this.name = name;
        this.exposure = exposure;
    }

    String getName() {
        return name;
    }

    int getExposure() {
        return exposure;
    }
}
