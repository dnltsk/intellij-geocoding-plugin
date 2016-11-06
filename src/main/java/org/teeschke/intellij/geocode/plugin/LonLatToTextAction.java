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

    private final LonLatParser lonLatParser = new LonLatParser();

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        final Editor editor = actionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = actionEvent.getRequiredData(CommonDataKeys.PROJECT);

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        if(!isTextSelected(selectionModel.getSelectedText())){
            return;
        }

        String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        final LonLat selectedLonLat = lonLatParser.parseLonLat(selectedText);
        if(selectedLonLat == null){
            return;
        }

        final Address address = new NominatimReverseGeocoder().lonLatToAddress(selectedLonLat);
        if(address == null || address.toString() == null) {
            return;
        }

        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                document.replaceString(start, end, address.toString());
            }
        };

        WriteCommandAction.runWriteCommandAction(project, replacementProcess);
        selectionModel.removeSelection();
    }

}
