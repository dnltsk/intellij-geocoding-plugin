package org.teeschke.intellij.geocode.plugin;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teeschke.intellij.geocode.plugin.nominatim.LonLat;

import static org.junit.Assert.*;

public class LonLatToTextTest {

    private final String CHARACTERS_TO_TRIM = "?=)(°^:\\|;/&%$§\"!abcdefgABCDEFG";
    private final String ALLOWED_DELIMITERS = "\t ,;:";
    private static LonLatToText lonLatToText;

    @BeforeClass
    public static void setUp(){
        lonLatToText = new LonLatToText();
    }

    @Test
    public void integers_are_getting_parsed_correctly(){
        LonLat lonLat = lonLatToText.parseLonLat("123, 12");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void doubles_are_getting_parsed_correctly(){
        LonLat lonLat = lonLatToText.parseLonLat("123.0, 12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void leading_special_characters_are_getting_trimmed(){
        LonLat lonLat = lonLatToText.parseLonLat(CHARACTERS_TO_TRIM+"123.0, 12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void ending_special_characters_are_getting_trimmed(){
        LonLat lonLat = lonLatToText.parseLonLat("123.0, 12.0"+CHARACTERS_TO_TRIM);
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void all_kinds_of_delimiters_can_be_used(){
        LonLat lonLat = lonLatToText.parseLonLat("123.0"+ALLOWED_DELIMITERS+"12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void leading_between_the_numbers_causes_null(){
        LonLat lonLat = lonLatToText.parseLonLat("123.0, abcd 12.0");
        assertEquals(null, lonLat);
    }

}