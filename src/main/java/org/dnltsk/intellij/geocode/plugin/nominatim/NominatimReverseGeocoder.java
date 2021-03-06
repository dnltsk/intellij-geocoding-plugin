package org.dnltsk.intellij.geocode.plugin.nominatim;

import org.codehaus.jackson.map.ObjectMapper;
import org.dnltsk.intellij.geocode.plugin.Address;
import org.dnltsk.intellij.geocode.plugin.LonLat;
import org.dnltsk.intellij.geocode.plugin.LonLatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NominatimReverseGeocoder {

    private static final Logger LOG = LoggerFactory.getLogger(NominatimReverseGeocoder.class);

    private static final int COUNTRY_LEVEL = 2;
    private static final int STATE_LEVEL = 4;
    private static final int STATE_DISTRICT = 5;
    private static final int COUNTY_LEVEL = 7;
    private static final int VILLAGE_LEVEL = 8;
    private static final int CITY_DISTRICT_LEVEL = 9;
    private static final int SUB_URBAN_LEVEL = 10;
    private static final int HOUSE_NUMBER_LEVEL = 18;

    private static final String NOMINATIM_ACCEPT_LANGUAGE_ENGLISH = "en";
    private static final String NOMINATIM_ACCEPT_LANGUAGE_GERMAN = "de";

    private static final String NOMINATIM_EMAIL = "intellij.geocoding.plugin@gmail.com";

    private static final String NOMINATIM_BASE_URL = "http://nominatim.openstreetmap.org/reverse?";
    private static final String NOMINATIM_DEFAULT_QUERY_STRING = "&format=json"
            + "&limit=1"
            + "&addressdetails=0"
            + "&accept-language=" + NOMINATIM_ACCEPT_LANGUAGE_ENGLISH
            + "&zoom=" + SUB_URBAN_LEVEL
            + "&email=" + NOMINATIM_EMAIL;

    private static final LonLatValidator lonLatValidator = new LonLatValidator();

    public Address lonLatToAddress(LonLat lonLat) {
        if (!lonLatValidator.validate(lonLat)) {
            LOG.debug("invalid LonLat: " + lonLat.toString());
            return null;
        }
        try {
            URL geocodeRequestUrl = createReverseGeocodeRequestUrl(lonLat);
            return fetchAddress(geocodeRequestUrl);
        } catch (IOException e) {
            LOG.error("error during lonLatToAddress with >" + lonLat + "<: " + e.getLocalizedMessage(), e);
            return null;
        }
    }

    private URL createReverseGeocodeRequestUrl(LonLat lonLat) throws IOException {
        String reverseGeocodeRequestUrl = NOMINATIM_BASE_URL + NOMINATIM_DEFAULT_QUERY_STRING
                + "&lon=" + lonLat.lon
                + "&lat=" + lonLat.lat;
        return new URL(reverseGeocodeRequestUrl);
    }

    private Address fetchAddress(URL reverseGeocodeUrl) throws IOException {
        try (InputStream is = reverseGeocodeUrl.openStream()) {
            return new ObjectMapper().readValue(is, Address.class);
        }
    }

}
