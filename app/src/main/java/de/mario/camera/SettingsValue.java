package de.mario.camera;

/**
 */
public enum SettingsValue {
    GEO_TAGGING("geotagging"), SHUTTER_SOUND("shutterSound"), PROCESS_HDR("processHdr"),
    SHUTTER_DELAY("shutterDelayTime"), GRID("grid"), TRACE("trace") ,
    PICTURE_SIZE("pictureSize"), ISO_KEY("iso-key"), ISO_VALUE("iso-value");

    private String value;

    SettingsValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
