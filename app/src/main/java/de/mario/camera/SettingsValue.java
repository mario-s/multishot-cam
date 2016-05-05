package de.mario.camera;

/**
 */
public enum SettingsValue {
    GEO_TAGGING("geotagging"), SHUTTER_SOUND("shutterSound"), PROCESS_HDR("processHdr"),
    SHUTTER_DELAY("shutterDelayTime"), GRID("grid");

    private String value;

    private SettingsValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
