package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

/**
 * Created by danielt on 05.11.16.
 */
abstract public class AbstractGeocodeAction extends AnAction {

    abstract public void actionPerformed(AnActionEvent e);

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        determineVisibility(e);
    }

    protected void determineVisibility(AnActionEvent actionEvent) {
        final Editor editor = actionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final SelectionModel selectionModel = editor.getSelectionModel();
        boolean isTextSelected = isTextSelected(selectionModel.getSelectedText());
        actionEvent.getPresentation().setEnabled(isTextSelected);
    }

    protected boolean isTextSelected(String selectedText) {
        return selectedText != null && selectedText.length() > 0;
    }

}
