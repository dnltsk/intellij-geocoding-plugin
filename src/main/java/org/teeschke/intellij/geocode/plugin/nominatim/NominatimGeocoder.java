package org.teeschke.intellij.geocode.plugin.nominatim;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teeschke.intellij.geocode.plugin.LonLat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class NominatimGeocoder {

    private static final Logger LOG = LoggerFactory.getLogger(NominatimGeocoder.class);

    private static final String NOMINATIM_BASE_URL = "http://nominatim.openstreetmap.org/search?";
    private static final String NOMINATIM_DEFAULT_QUERY_STRING = "&format=json&limit=1&polygon=0&addressdetails=0&email=intellij.geocoding.plugin@gmail.com";

    public LonLat addressToLonLat(String addressQuery) {
        if (addressQuery == null || addressQuery.length() == 0) {
            return null;
        }
        try {
            URL geocodeRequestUrl = createGeocodeRequestUrl(addressQuery);
            return fetchLonLat(geocodeRequestUrl);
        } catch (IOException e) {
            LOG.error("Error during addressToLonLat with >" + addressQuery + "<: " + e.getLocalizedMessage(), e);
            return null;
        }
    }

    private URL createGeocodeRequestUrl(String addressQuery) throws IOException {
        String encodedAddressQuery = URLEncoder.encode(addressQuery, "utf-8");
        String geocodeRequestUrl =
                NOMINATIM_BASE_URL + NOMINATIM_DEFAULT_QUERY_STRING
                        + "&q=" + encodedAddressQuery;
        return new URL(geocodeRequestUrl);
    }

    private LonLat fetchLonLat(URL geocodeUrl) throws IOException {
        try (InputStream is = geocodeUrl.openStream()) {
            LonLat[] lonLats = new ObjectMapper().readValue(is, LonLat[].class);
            if (lonLats == null || lonLats.length == 0) {
                return null;
            }
            return lonLats[0];
        }
    }

}
