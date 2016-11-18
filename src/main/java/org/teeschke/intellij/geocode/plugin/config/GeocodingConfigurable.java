package org.teeschke.intellij.geocode.plugin.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeocodingConfigurable implements SearchableConfigurable {

    private static final Logger LOG = LoggerFactory.getLogger(GeocodingConfigurable.class);

    private DefaultComboBoxModel orderOptionsModel = new DefaultComboBoxModel<>(LonLatOrder.values());
    private GeocodingConfigManager configManager = new GeocodingConfigManager();

    private boolean isModified = false;

    @Override
    public String getDisplayName() {
        return "Geocoding Plugin";
    }

    @Override
    public String getHelpTopic() {
        return "preference.teeschke.GeocodingConfigurable";
    }

    @Override
    public String getId() {
        return "preference.teeschke.GeocodingConfigurable";
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Override
    public JComponent createComponent() {
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
        builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        builder.appendColumn("right:pref");
        builder.appendColumn("3dlu");
        builder.appendColumn("fill:max(pref; 100px)");
        builder.append("lat/lon order:", createOrderComboBox());
        return builder.getPanel();
    }

    @NotNull
    private ComboBox<LonLatOrder> createOrderComboBox() {
        ComboBox<LonLatOrder> orderOptionComboBox = new ComboBox<>(orderOptionsModel);
        orderOptionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LonLatOrder selectedOrder = (LonLatOrder) orderOptionsModel.getSelectedItem();
                LonLatOrder storedOrder = configManager.getLonLatOrder();
                if (!selectedOrder.equals(storedOrder)) {
                    isModified = true;
                }
            }
        });
        return orderOptionComboBox;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        configManager.getGeocodingConfig().setStoredLonLatOrder((LonLatOrder) orderOptionsModel.getSelectedItem());
        isModified = false;
    }

    @Override
    public void reset() {
        orderOptionsModel.setSelectedItem(configManager.getLonLatOrder());
    }

    @Override
    public void disposeUIResources() {
    }
}
