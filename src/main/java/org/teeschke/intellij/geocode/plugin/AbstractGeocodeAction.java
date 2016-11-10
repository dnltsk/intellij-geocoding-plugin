package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by danielt on 05.11.16.
 */
abstract public class AbstractGeocodeAction extends AnAction {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractGeocodeAction.class);

    abstract public void actionPerformed(AnActionEvent e);

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        determineVisibility(e);
    }

    protected void determineVisibility(AnActionEvent actionEvent) {
        try {
            Editor editor = getCurrentEditor(actionEvent);
            if(editor == null){
                return;
            }
            actionEvent.getPresentation().setEnabled(hasEditorSelectedText(editor));
        } catch (Exception e) {
            LOG.error("Something went wrong during determineVisibility(): " + e.getLocalizedMessage(), e);
        }
    }

    protected Editor getCurrentEditor(AnActionEvent actionEvent) {
        DataContext dataContext = actionEvent.getDataContext();
        return CommonDataKeys.EDITOR.getData(dataContext);
    }

    protected boolean hasEditorSelectedText(Editor editor) {
        if(editor == null){
            return false;
        }
        final SelectionModel selectionModel = editor.getSelectionModel();
        return isTextSelected(selectionModel.getSelectedText());
    }

    protected boolean isTextSelected(String selectedText) {
        return selectedText != null && selectedText.length() > 0;
    }

}
