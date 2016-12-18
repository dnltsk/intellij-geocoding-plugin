package org.dnltsk.intellij.geocode.plugin;

import org.dnltsk.intellij.geocode.plugin.config.GeocodingConfigManager;
import org.dnltsk.intellij.geocode.plugin.config.LonLatOrder;

public class LonLatRenderer {

    private GeocodingConfigManager configManager = new GeocodingConfigManager();

    public String renderLonLat(LonLat lonLat) {
        LonLatOrder lonLatOrder = configManager.getLonLatOrder();
        switch (lonLatOrder) {
            case LAT_LON:
                return lonLat.lat + ", " + lonLat.lon;
            case LON_LAT:
                return lonLat.lon + ", " + lonLat.lat;
            default:
                throw new RuntimeException("Unknown LonLatOrder: " + lonLatOrder);
        }
    }

}
