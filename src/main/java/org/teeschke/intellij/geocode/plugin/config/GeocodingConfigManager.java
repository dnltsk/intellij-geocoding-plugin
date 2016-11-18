package org.teeschke.intellij.geocode.plugin.config;

import com.intellij.openapi.components.ServiceManager;

public class GeocodingConfigManager {

    public LonLatOrder getLonLatOrder(){
        GeocodingConfig geocodingConfig = ServiceManager.getService(GeocodingConfig.class);
        if(geocodingConfig.getStoredLonLatOrder() == null){
            return LonLatOrder.LAT_LON.DEFAULT_ORDER;
        }
        return geocodingConfig.getStoredLonLatOrder();
    }

    public GeocodingConfig getGeocodingConfig(){
        return ServiceManager.getService(GeocodingConfig.class);
    }


}
