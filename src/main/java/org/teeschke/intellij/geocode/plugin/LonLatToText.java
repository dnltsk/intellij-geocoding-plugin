package org.teeschke.intellij.geocode.plugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;
import org.teeschke.intellij.geocode.plugin.nominatim.Address;
import org.teeschke.intellij.geocode.plugin.nominatim.LonLat;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimGeocoder;
import org.teeschke.intellij.geocode.plugin.nominatim.NominatimReverseGeocoder;

import java.util.regex.Pattern;

public class LonLatToText extends AbstractGeocodeAction {

    private Pattern LON_LAT_TRIM_BEGIN = Pattern.compile("^[^\\d-]*"); //all non-numbers plus dash from beginning
    private Pattern LON_LAT_TRIM_END = Pattern.compile("[^\\d-]*$"); //all non-numbers plus dash from end
    private Pattern LON_LAT_DELIMITERS = Pattern.compile("[\\s,;:]+"); //whitespaces, comma, semicolon, colon

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

        final LonLat selectedLonLat = parseLonLat(selectedText);
        if(selectedLonLat == null){
            return;
        }

        Runnable replacementProcess = new Runnable() {
            @Override
            public void run() {
                Address address = new NominatimReverseGeocoder().lonLatToAddress(selectedLonLat);
                if(address == null || address.display_name == null) {
                    return;
                }
                document.replaceString(start, end, address.toString());
            }
        };

        WriteCommandAction.runWriteCommandAction(project, replacementProcess);
        selectionModel.removeSelection();
    }

    LonLat parseLonLat(String selectedText){
        selectedText = trimSelectedText(selectedText);
        return separateLonLat(selectedText);
    }

    private String trimSelectedText(String selectedText) {
        selectedText = selectedText.replaceAll(LON_LAT_TRIM_BEGIN.pattern(), "");
        selectedText = selectedText.replaceAll(LON_LAT_TRIM_END.pattern(), "");
        return selectedText;
    }

    private LonLat separateLonLat(String selectedText) {
        String[] lonLatSplit = selectedText.split(LON_LAT_DELIMITERS.pattern());
        if(lonLatSplit == null || lonLatSplit.length != 2) {
            return null;
        }
        LonLat lonLat = new LonLat();
        lonLat.lon = Double.parseDouble(lonLatSplit[0]);
        lonLat.lat = Double.parseDouble(lonLatSplit[1]);
        return lonLat;
    }

}
