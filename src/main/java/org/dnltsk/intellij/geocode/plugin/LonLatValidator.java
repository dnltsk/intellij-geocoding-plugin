package org.dnltsk.intellij.geocode.plugin;

public class LonLatValidator {

    public boolean validate(LonLat lonLat) {
        if (lonLat == null || lonLat.lon == null || lonLat.lat == null) {
            return false;
        }
        if (Math.abs(lonLat.lon) > 180.0 || Math.abs(lonLat.lat) > 90.0) {
            return false;
        }
        return true;
    }

}
