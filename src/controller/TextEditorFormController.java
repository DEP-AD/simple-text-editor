package controller;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import util.FXUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextEditorFormController {

    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;
    public TextField txtReplace;
    public AnchorPane pneReplace;
    public TextField txtSearch1;

    private int findOffSet = -1;
    private final List<Index> searchList = new ArrayList<>();

    public void initialize() {
        pneFind.setVisible(false);

        ChangeListener textListener = (ChangeListener<String>) (observable, oldValue, newValue) -> {
            searchMatches(newValue);
        };

        txtSearch.textProperty().addListener(textListener);
    }

    private void searchMatches(String query){
        FXUtil.highlightOnTextArea(txtEditor, query, Color.web("yellow",0.8));

        Pattern regExp = Pattern.compile(query);
        Matcher matcher = regExp.matcher(txtEditor.getText());

        searchList.clear();

        while (matcher.find()){
            searchList.add(new Index(matcher.start(), matcher.end()));
        }

        if(searchList.isEmpty()){
            findOffSet=-1;
        }


    }

    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
        txtEditor.clear();
        txtEditor.requestFocus();
    }

    public void mnuItemQuit_OnAction(ActionEvent actionEvent) {
    }

    public void mnuItemFind_OnAction(ActionEvent actionEvent) {
        pneFind.setVisible(true);
        txtSearch.requestFocus();
    }

    public void mnuItemReplace_OnAction(ActionEvent actionEvent) {
    }

    public void mnuItemSelectAll_OnAction(ActionEvent actionEvent) {
        txtEditor.selectAll();
    }

    public void btnFindNext_OnAction(ActionEvent actionEvent) {
        if (!searchList.isEmpty()) {
            if (findOffSet == -1) {
                findOffSet = 0;
            }
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
            findOffSet++;
            if (findOffSet >= searchList.size()) {
                findOffSet = 0;
            }
        }
    }

    public void btnFindPreview_OnAction(ActionEvent actionEvent) {
        if (!searchList.isEmpty()) {
            if (findOffSet == -1) {
                findOffSet = searchList.size() - 1;
            }
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
            findOffSet--;
            if (findOffSet < 0) {
                findOffSet = searchList.size() - 1;
            }
        }
    }

    public void btnReplace_OnAction(ActionEvent actionEvent) {
    }

    public void btnReplaceAll_OnAction(ActionEvent actionEvent) {
    }
}

class Index {
    int startingIndex;
    int endIndex;

    public Index(int startingIndex, int endIndex) {
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }
}
