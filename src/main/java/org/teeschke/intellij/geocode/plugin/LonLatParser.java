package org.teeschke.intellij.geocode.plugin;

import java.util.regex.Pattern;

public class LonLatParser {

    private Pattern LON_LAT_TRIM_BEGIN = Pattern.compile("^[^\\d-]*"); //all non-numbers plus dash from beginning
    private Pattern LON_LAT_TRIM_END = Pattern.compile("[^\\d-]*$"); //all non-numbers plus dash from end
    private Pattern LON_LAT_DELIMITERS = Pattern.compile("[\\s,;:]+"); //whitespaces, comma, semicolon, colon

    public LonLat parseLonLat(String text){
        text = trimSelectedText(text);
        return separateLonLat(text);
    }

    private String trimSelectedText(String text) {
        text = text.replaceAll(LON_LAT_TRIM_BEGIN.pattern(), "");
        text = text.replaceAll(LON_LAT_TRIM_END.pattern(), "");
        return text;
    }

    private LonLat separateLonLat(String text) {
        String[] lonLatSplit = text.split(LON_LAT_DELIMITERS.pattern());
        if(lonLatSplit.length != 2) {
            return null;
        }
        LonLat lonLat = new LonLat();
        lonLat.lon = Double.parseDouble(lonLatSplit[0]);
        lonLat.lat = Double.parseDouble(lonLatSplit[1]);
        return lonLat;
    }

}
