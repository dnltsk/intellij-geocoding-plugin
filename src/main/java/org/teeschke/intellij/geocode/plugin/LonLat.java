package org.teeschke.intellij.geocode.plugin;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LonLat {

    public Double lat;
    public Double lon;

    @Override
    public String toString() {
        return lon + ", " + lat;
    }

}