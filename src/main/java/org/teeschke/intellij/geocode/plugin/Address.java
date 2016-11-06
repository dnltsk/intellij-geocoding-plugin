package org.teeschke.intellij.geocode.plugin;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("display_name")
    public String displayName;

    @Override
    public String toString() {
        return displayName;
    }

}