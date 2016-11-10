package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimGeocoder;

public class TextToLonLatAction extends AbstractGeocodeAction {

    private static final NominatimGeocoder nominatimGeocoder = new NominatimGeocoder();

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        final Editor editor = getCurrentEditor(actionEvent);
        if(!hasEditorSelectedText(editor)){
            return;
        }

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final String selectedText = selectionModel.getSelectedText();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                LonLat lonLat = nominatimGeocoder.addressToLonLat(selectedText);
                if(lonLat != null) {
                    document.replaceString(start, end, lonLat.toString());
                }
            }
        };

        final Project project = actionEvent.getRequiredData(CommonDataKeys.PROJECT);
        WriteCommandAction.runWriteCommandAction(project, replacementProcess);
        selectionModel.removeSelection();
    }

}
