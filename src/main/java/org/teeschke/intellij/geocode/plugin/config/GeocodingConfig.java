package org.teeschke.intellij.geocode.plugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "GeocodingConfig",
        storages = {
                @Storage("geocodingConfig.xml")}
)
public class GeocodingConfig implements PersistentStateComponent<GeocodingConfig> {

    private LonLatOrder storedLonLatOrder;

    public LonLatOrder getStoredLonLatOrder() {
        return storedLonLatOrder;
    }

    public void setStoredLonLatOrder(LonLatOrder storedLonLatOrder) {
        this.storedLonLatOrder = storedLonLatOrder;
    }

    @Nullable
    @Override
    public GeocodingConfig getState() {
        return this;
    }

    @Override
    public void loadState(GeocodingConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
