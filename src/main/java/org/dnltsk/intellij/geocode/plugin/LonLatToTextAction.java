package org.dnltsk.intellij.geocode.plugin;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.SelectionModel;
import org.dnltsk.intellij.geocode.plugin.nominatim.NominatimReverseGeocoder;

public class LonLatToTextAction extends AbstractGeocodeAction {

    private static final LonLatParser lonLatParser = new LonLatParser();
    private static final NominatimReverseGeocoder nominatimReverseGeocoder = new NominatimReverseGeocoder();

    protected Runnable getReplacementRunnable(final Document document, SelectionModel selectionModel) {
        final String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        final Address address = extractLonLatAndGetAddress(selectedText);
        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                if (address == null) {
                    return;
                }
                document.replaceString(
                        start,
                        end,
                        address.toString());
            }
        };
        return replacementProcess;
    }

    private Address extractLonLatAndGetAddress(String selectedText) {
        LonLat selectedLonLat = lonLatParser.parseLonLat(selectedText);
        if (selectedLonLat == null) {
            return null;
        }
        return nominatimReverseGeocoder.lonLatToAddress(selectedLonLat);
    }

}
