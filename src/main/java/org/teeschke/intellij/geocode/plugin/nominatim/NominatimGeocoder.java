package org.teeschke.intellij.geocode.plugin.nominatim;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jetbrains.annotations.NotNull;

public class NominatimGeocoder {

    private static final String NOMINATIM_BASE_URL = "http://nominatim.openstreetmap.org/search?";
    private static final String NOMINATIM_DEFAULT_QUERY_STRING = "&format=json&limit=1&polygon=0&addressdetails=0&email=intellij-geocode-plugin@gmail.com";

    private static final Logger LOG = Logger.getLogger(NominatimGeocoder.class.toString());

    public LonLat addressToLonLat(String addressQuery) {
        if (addressQuery == null || addressQuery.length() == 0 ) {
            return null;
        }
        try {
            URL geocodeRequestUrl = createGeocodeRequestUrl(addressQuery);
            return fetchLonLat(geocodeRequestUrl);
        } catch (IOException ex) {
            LOG.log(Level.INFO, ex.getLocalizedMessage(), ex);
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

    private LonLat fetchLonLat(URL geocodeUrl) throws IOException{
        try(InputStream is = geocodeUrl.openStream()){
            LonLat[] lonLats = new ObjectMapper().readValue(is, LonLat[].class);
            if(lonLats == null || lonLats.length == 0) {
                return null;
            }
            return lonLats[0];
        }
    }

}
