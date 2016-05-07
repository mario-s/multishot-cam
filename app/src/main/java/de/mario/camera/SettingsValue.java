package de.mario.camera;

/**
 */
public enum SettingsValue {
    GEO_TAGGING("geotagging"), SHUTTER_SOUND("shutterSound"), PROCESS_HDR("processHdr"),
    SHUTTER_DELAY("shutterDelayTime"), GRID("grid"), TRACE("trace") ,
    IMAGE_RESOLUTION("imageResolution");

    private String value;

    SettingsValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
