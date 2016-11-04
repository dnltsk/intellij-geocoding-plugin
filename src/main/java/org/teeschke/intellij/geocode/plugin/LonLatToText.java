package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.teeschke.intellij.geocode.plugin.nominatim.Address;
import org.teeschke.intellij.geocode.plugin.nominatim.LonLat;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimGeocoder;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimReverseGeocoder;

public class LonLatToText extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        final Editor editor = actionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = actionEvent.getRequiredData(CommonDataKeys.PROJECT);

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        final String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                String[] parts = selectedText.split(" ");
                LonLat lonLat = new LonLat();
                lonLat.lon = Double.parseDouble(parts[0]);
                lonLat.lat = Double.parseDouble(parts[1]);
                Address address = new NominatimReverseGeocoder().lonLatToAddress(lonLat);
                if(address != null) {
                    document.replaceString(start, end, address.toString());
                }
            }
        };

        WriteCommandAction.runWriteCommandAction(project, replacementProcess);
        selectionModel.removeSelection();
    }
}
