package org.teeschke.intellij.geocode.plugin.nominatim;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NominatimReverseGeocoder {

    private static final int COUNTRY_LEVEL = 2;
    private static final int STATE_LEVEL = 4;
    private static final int STATE_DISTRICT = 5;
    private static final int COUNTY_LEVEL = 7;
    private static final int VILLAGE_LEVEL = 8;
    private static final int CITY_DISTRICT_LEVEL = 9;
    private static final int SUB_URBAN_LEVEL = 10;
    private static final int HOUSE_NUMBER_LEVEL = 18;

    private static final String NOMINATIM_BASE_URL = "http://nominatim.openstreetmap.org/reverse?";
    private static final String NOMINATIM_DEFAULT_QUERY_STRING = "&format=json&limit=1&zoom="+SUB_URBAN_LEVEL+"&addressdetails=0&email=intellij-geocode-plugin@gmail.com";

    private static final Logger LOG = Logger.getLogger(NominatimReverseGeocoder.class.toString());

    public Address lonLatToAddress(LonLat lonLat) {
        if(!isValidCoordinate(lonLat)){
            LOG.warning("invalid LonLat: "+lonLat.toString());
            return null;
        }
        try {
            URL geocodeRequestUrl = createReverseGeocodeRequestUrl(lonLat);
            return fetchAddress(geocodeRequestUrl);
        } catch (IOException ex) {
            LOG.log(Level.INFO, ex.getLocalizedMessage(), ex);
            return null;
        }
    }

    private boolean isValidCoordinate(LonLat lonLat) {
        if (lonLat == null || lonLat.lon == null || lonLat.lat == null) {
            return false;
        }
        if (Math.abs(lonLat.lon) > 180.0 || Math.abs(lonLat.lat) > 90.0) {
            return false;
        }
        return true;
    }

    private URL createReverseGeocodeRequestUrl(LonLat lonLat) throws IOException {
        String reverseGeocodeRequestUrl = NOMINATIM_BASE_URL + NOMINATIM_DEFAULT_QUERY_STRING
                + "&lon=" + lonLat.lon
                + "&lat=" + lonLat.lat;
        return new URL(reverseGeocodeRequestUrl);
    }

    private Address fetchAddress(URL reverseGeocodeUrl) throws IOException{
        try(InputStream is = reverseGeocodeUrl.openStream()){
            Address address = new ObjectMapper().readValue(is, Address.class);
            return address;
        }
    }

}
