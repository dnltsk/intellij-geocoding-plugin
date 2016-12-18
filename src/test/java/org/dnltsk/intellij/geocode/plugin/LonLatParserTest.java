package org.dnltsk.intellij.geocode.plugin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class LonLatParserTest extends LightPlatformCodeInsightFixtureTestCase {

    private final String CHARACTERS_TO_TRIM = "?=)(°^:\\|;/&%$§\"!abcdefgABCDEFG";
    private final String ALLOWED_DELIMITERS = "\t ,;:";
    private static LonLatParser lonLatParser;

    @Before
    public void setUp(){
        lonLatParser = new LonLatParser();
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void integers_are_getting_parsed_correctly() {
        LonLat lonLat = lonLatParser.parseLonLat("123, 12");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void doubles_are_getting_parsed_correctly() {
        LonLat lonLat = lonLatParser.parseLonLat("123.0, 12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void leading_special_characters_are_getting_trimmed() {
        LonLat lonLat = lonLatParser.parseLonLat(CHARACTERS_TO_TRIM + "123.0, 12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void ending_special_characters_are_getting_trimmed() {
        LonLat lonLat = lonLatParser.parseLonLat("123.0, 12.0" + CHARACTERS_TO_TRIM);
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void all_kinds_of_delimiters_can_be_used() {
        LonLat lonLat = lonLatParser.parseLonLat("123.0" + ALLOWED_DELIMITERS + "12.0");
        assertNotEquals(null, lonLat);
        assertEquals(new Double(123.0), lonLat.lon);
        assertEquals(new Double(12.0), lonLat.lat);
    }

    @Test
    public void character_between_the_numbers_causes_null() {
        LonLat lonLat = lonLatParser.parseLonLat("123.0, abcd 12.0");
        assertEquals(null, lonLat);
    }

    /**
     * ignored until ServiceManager.getService() can get mocked
     */
    @Test @Ignore
    public void negative_lats_can_be_parsed() {
        LonLat lonLat = lonLatParser.parseLonLat("0.3149312, -79.4063074");
        assertNotEquals(null, lonLat);
    }

}