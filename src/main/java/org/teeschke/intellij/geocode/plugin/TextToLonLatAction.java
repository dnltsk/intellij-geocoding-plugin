package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.SelectionModel;
import org.jetbrains.annotations.NotNull;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimGeocoder;

public class TextToLonLatAction extends AbstractGeocodeAction {

    private static final NominatimGeocoder nominatimGeocoder = new NominatimGeocoder();
    private static final LonLatRenderer lonLatRenderer = new LonLatRenderer();

    @Override
    @NotNull
    protected Runnable getReplacementRunnable(final Document document, SelectionModel selectionModel) {
        final String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        final LonLat lonLat = nominatimGeocoder.addressToLonLat(selectedText);
        return new Runnable() {
            @Override
            public void run() {
                if (lonLat == null) {
                    return;
                }
                document.replaceString(
                        start,
                        end,
                        lonLatRenderer.renderLonLat(lonLat));

            }
        };
    }

}
