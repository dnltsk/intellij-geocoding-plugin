package org.teeschke.intellij.geocode.plugin.nominatim;

import org.junit.Test;
import org.teeschke.intellij.geocode.plugin.LonLat;

import static org.junit.Assert.assertNotNull;

public class NominatimGeocoderTest {

    @Test
    public void Beijing_can_be_geocoded(){
        LonLat beijing = new NominatimGeocoder().addressToLonLat("Beijing");
        assertNotNull(beijing);
    }

}