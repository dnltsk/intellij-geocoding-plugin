package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimReverseGeocoder;

public class LonLatToTextAction extends AbstractGeocodeAction {

    private static final LonLatParser lonLatParser = new LonLatParser();
    private static final NominatimReverseGeocoder nominatimReverseGeocoder = new NominatimReverseGeocoder();

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        final Editor editor = getCurrentEditor(actionEvent);
        if (!hasEditorSelectedText(editor)) {
            return;
        }

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        final LonLat selectedLonLat = lonLatParser.parseLonLat(selectedText);
        if (selectedLonLat == null) {
            return;
        }

        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                final Address address = nominatimReverseGeocoder.lonLatToAddress(selectedLonLat);
                if (address == null || address.toString() == null) {
                    return;
                }
                document.replaceString(start, end, address.toString());
            }
        };

        final Project project = actionEvent.getRequiredData(CommonDataKeys.PROJECT);
        WriteCommandAction.runWriteCommandAction(project, replacementProcess);
        selectionModel.removeSelection();
    }

}
