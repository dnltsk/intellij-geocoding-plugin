package org.teeschke.intellij.geocode.plugin.nominatim;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teeschke.intellij.geocode.plugin.Address;
import org.teeschke.intellij.geocode.plugin.LonLat;

import static org.junit.Assert.assertEquals;

public class NominatimReverseGeocoderTest {

    private static NominatimReverseGeocoder reverseGeocoder;

    @BeforeClass
    public static void setUp(){
        reverseGeocoder = new NominatimReverseGeocoder();
    }

    @Test
    public void Beijing_can_get_reverse_geocoded(){
        LonLat beijingLonLat = new LonLat();
        beijingLonLat.lon = 116.383333;
        beijingLonLat.lat = 39.933333;
        Address beijingAddress = reverseGeocoder.lonLatToAddress(beijingLonLat);
        assertEquals("Beijing, Dongcheng District, Beijing, 100010, China", beijingAddress.displayName);
    }

}