package de.mario.camera.controller;

/**
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
