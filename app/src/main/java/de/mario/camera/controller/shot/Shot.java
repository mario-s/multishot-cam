
package de.mario.camera.controller.shot;

/**
 */
final class Shot {
    private final String name;
    private int exposure;
    private boolean flash;

    Shot(String name) {
        this.name = name;
    }

    Shot(Shot other) {
        this(other.getName());
        this.setExposure(other.getExposure());
        this.setFlash(other.isFlash());
    }

    String getName() {
        return name;
    }

    void setExposure(int exposure) {
        this.exposure = exposure;
    }

    int getExposure() {
        return exposure;
    }

    void setFlash(boolean flash) {
        this.flash = flash;
    }

    boolean isFlash() {
        return flash;
    }
}
