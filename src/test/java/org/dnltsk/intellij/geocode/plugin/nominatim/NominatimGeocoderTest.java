package org.dnltsk.intellij.geocode.plugin.nominatim;

import org.dnltsk.intellij.geocode.plugin.LonLat;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class NominatimGeocoderTest {

    @Test
    public void Beijing_can_be_geocoded() {
        LonLat beijing = new NominatimGeocoder().addressToLonLat("Beijing");
        assertNotNull(beijing);
    }

}