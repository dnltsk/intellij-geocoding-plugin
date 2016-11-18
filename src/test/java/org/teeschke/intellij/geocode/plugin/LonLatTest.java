package org.teeschke.intellij.geocode.plugin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LonLatTest {

    @Test
    public void lon_lat_are_in_correct_order() {
        LonLat lonLat = new LonLat();
        lonLat.lat = 1.0;
        lonLat.lon = 2.0;
        assertEquals(lonLat.toString(), "2.0, 1.0");
    }

}
