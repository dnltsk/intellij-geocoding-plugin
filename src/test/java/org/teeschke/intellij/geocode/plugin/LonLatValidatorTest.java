package org.teeschke.intellij.geocode.plugin;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LonLatValidatorTest {

    private static LonLatValidator lonLatValidator;

    @BeforeClass
    public static void setUp() {
        lonLatValidator = new LonLatValidator();
    }

    @Test
    public void lon_lat_inside_range_are_valid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = 0.0;
        lonlat.lat = 0.0;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(true, isValid);
    }

    @Test
    public void too_far_northwards_is_unvalid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = 0.0;
        lonlat.lat = 90.1;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(false, isValid);
    }

    @Test
    public void too_far_eastwards_is_unvalid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = 180.1;
        lonlat.lat = 0.0;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(false, isValid);
    }

    @Test
    public void too_far_southwards_is_unvalid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = 0.0;
        lonlat.lat = -90.1;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(false, isValid);
    }

    @Test
    public void too_far_westwards_is_unvalid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = -180.1;
        lonlat.lat = 0.0;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(false, isValid);
    }

    @Test
    public void on_south_west_border_is_valid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = -180.0;
        lonlat.lat = -90.0;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(true, isValid);
    }

    @Test
    public void on_north_east_border_is_valid() {
        LonLat lonlat = new LonLat();
        lonlat.lon = 180.0;
        lonlat.lat = 90.0;
        boolean isValid = lonLatValidator.validate(lonlat);
        assertEquals(true, isValid);
    }

}