package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TextEditorFormController {

    public TextArea txtEditor;
    public AnchorPane pneFind;
    public TextField txtSearch;

    public void initialize(){
        pneFind.setVisible(false);

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
    }

    public void btnFindPreview_OnAction(ActionEvent actionEvent) {
    }
}
