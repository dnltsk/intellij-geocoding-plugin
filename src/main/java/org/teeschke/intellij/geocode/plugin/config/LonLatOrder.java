package org.teeschke.intellij.geocode.plugin.config;

public enum LonLatOrder {

    LON_LAT("lon, lat"),
    LAT_LON("lat, lon");

    public String title;

    public static final LonLatOrder DEFAULT_ORDER = LON_LAT;

    LonLatOrder(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
