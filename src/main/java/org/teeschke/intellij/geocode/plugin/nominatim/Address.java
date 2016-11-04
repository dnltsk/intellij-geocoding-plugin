package org.teeschke.intellij.geocode.plugin.nominatim;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    public String display_name;

    @Override
    public String toString() {
        return display_name;
    }

}