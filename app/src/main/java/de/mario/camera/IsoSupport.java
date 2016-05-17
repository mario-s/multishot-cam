package de.mario.camera;

import android.hardware.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles the ISO support. However there is no standard in the Android API.
 *
 * @see http://stackoverflow.com/questions/2978095/android-camera-api-iso-setting.
 */
public final class IsoSupport {
    private final Camera.Parameters params;

    IsoSupport(Camera camera) {
        this(camera.getParameters());
    }

    IsoSupport(Camera.Parameters params) {
        this.params = params;
    }

    /**
     * Returns a list of support ISO values for this device. The list can also be empty.
     *
     * @return
     */
    public List<String> getIsoValues() {
        String isoValues = findIsoValues();
        List<String> values = new ArrayList<>();
        if (isoValues != null && !isoValues.isEmpty()) {
            String[] isos = isoValues.split(",");
            values = Arrays.asList(isos);
        }
        return values;
    }

    /**
     * Tries to look for available ISO values
     *
     * @return either iso values or null
     */
    private String findIsoValues() {
        String iso_values = params.get("iso-values");
        if (iso_values == null) {
            iso_values = params.get("iso-mode-values"); // Galaxy Nexus
            if (iso_values == null) {
                iso_values = params.get("iso-speed-values"); // Micromax A101
                if (iso_values == null)
                    iso_values = params.get("nv-picture-iso-values"); // LG dual P990
            }
        }
        return iso_values;
    }
}
