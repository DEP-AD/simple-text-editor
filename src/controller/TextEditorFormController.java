package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextEditorFormController {

    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;

    private int findOffSet = -1;
    private List<Index> searchList   = new ArrayList<>();
    private int searchIndex = 0;

    public void initialize(){
        pneFind.setVisible(false);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(), matcher.end()));
                }
            }
            catch(PatternSyntaxException e){

            }
        });
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
        if(!searchList.isEmpty()) {
            if(findOffSet==-1){
                findOffSet=0;
            }
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
            findOffSet++;
            if(findOffSet>=searchList.size()){
                findOffSet=0;
            }
        }
    }

    public void btnFindPreview_OnAction(ActionEvent actionEvent) {
        if(!searchList.isEmpty()) {
            if(findOffSet==-1){
                findOffSet=searchList.size()-1;
            }
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
            findOffSet--;
            if(findOffSet<0){
                findOffSet=searchList.size()-1;
            }
        }
    }
}

class Index{
    int startingIndex;
    int endIndex;

    public Index(int startingIndex, int endIndex) {
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }
}
