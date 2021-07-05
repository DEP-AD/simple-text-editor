package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        pneReplace.setVisible(false);

        //Local Inner Class
        /*class TextListener implements ChangeListener<String>{

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                FXUtil.highlightOnTextArea(txtEditor, newValue, Color.web("yellow",0.8));

                try {
                    Pattern regExp = Pattern.compile(newValue);
                    Matcher matcher = regExp.matcher(txtEditor.getText());

                    searchList.clear();

                    while (matcher.find()) {
                        searchList.add(new Index(matcher.start(), matcher.end()));
                    }
                }catch (PatternSyntaxException e){

                }
            }
        }*/

        //Method 1 - create two objects
        /*txtSearch.textProperty().addListener(new TextListener(txtEditor,searchList));
        txtSearch1.textProperty().addListener(new TextListener(txtEditor,searchList));*/

        //Method 2 - create one object
        /*TextListener textListener = new TextListener(txtEditor,searchList);
        txtSearch.textProperty().addListener(textListener);
        txtSearch1.textProperty().addListener(textListener);*/


        //Method 3 - No arguments/ not passing data <- using regular inner class
/*        ChangeListener textListener = new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                FXUtil.highlightOnTextArea(txtEditor, newValue, Color.web("yellow",0.8));

                try {
                    Pattern regExp = Pattern.compile(newValue);
                    Matcher matcher = regExp.matcher(txtEditor.getText());

                    searchList.clear();

                    while (matcher.find()) {
                        searchList.add(new Index(matcher.start(), matcher.end()));
                    }
                }catch (PatternSyntaxException e){

                }
            }
        };*/

        //Method 4 - using lamda expresion
        ChangeListener textListener = (ChangeListener<String>) (observable, oldValue, newValue) -> {
                searchMatches(newValue);
        };
        txtSearch.textProperty().addListener(textListener);
        txtSearch1.textProperty().addListener(textListener);
    }

    private void searchMatches(String query){
        FXUtil.highlightOnTextArea(txtEditor, query, Color.web("yellow",0.8));

        try {
            Pattern regExp = Pattern.compile(query);
            Matcher matcher = regExp.matcher(txtEditor.getText());

            searchList.clear();

            while (matcher.find()) {
                searchList.add(new Index(matcher.start(), matcher.end()));
            }

            if(searchList.isEmpty()){
                findOffSet = -1;
            }
        }catch (PatternSyntaxException e){

        }
    }

    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
        txtEditor.clear();
        txtEditor.requestFocus();
    }

    public void mnuItemQuit_OnAction(ActionEvent actionEvent) {
    }

    public void mnuItemFind_OnAction(ActionEvent actionEvent) {
        if(pneReplace.isVisible()){
            pneReplace.setVisible(false);
        }
        pneFind.setVisible(true);
        txtSearch.requestFocus();
    }

    public void mnuItemReplace_OnAction(ActionEvent actionEvent) {
        if(pneFind.isVisible()){
            pneFind.setVisible(false);
        }
        pneReplace.setVisible(true);
        txtSearch1.requestFocus();
    }

    public void mnuItemSelectAll_OnAction(ActionEvent actionEvent) {
        txtEditor.selectAll();
    }

    public void btnFindNext_OnAction(ActionEvent actionEvent) {
        if (!searchList.isEmpty()) {
            /*if (findOffSet == -1) {
                findOffSet = 0;
            }else {*/
                findOffSet++;
                if (findOffSet >= searchList.size()) {
                    findOffSet = 0;
                }
            //}
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
        }
    }

    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {
        if (!searchList.isEmpty()) {
            /*if (findOffSet == -1) {
                findOffSet = searchList.size() - 1;
            }else{*/
                findOffSet--;
                if (findOffSet < 0) {
                    findOffSet = searchList.size() - 1;
                }
           // }
            txtEditor.selectRange(searchList.get(findOffSet).startingIndex, searchList.get(findOffSet).endIndex);
        }
    }

    public void btnReplace_OnAction(ActionEvent actionEvent) {
        if(findOffSet == -1) return;
        txtEditor.replaceText(searchList.get(findOffSet).startingIndex,searchList.get(findOffSet).endIndex,txtReplace.getText());
        searchMatches(txtSearch1.getText());
    }

    public void btnReplaceAll_OnAction(ActionEvent actionEvent) {
        //1st method
            /*Sring text = txtEditor.getText();
            String replacedText = text.replaceAll(txtSearch1.getText(), txtReplace.getText());
            txtEditor.setText(replacedText);*/

//        try {
//            String replacedText = Pattern.compile(txtSearch1.getText()).matcher(txtEditor.getText()).replaceAll(txtReplace.getText());
//            txtEditor.setText(replacedText);
//        }catch (PatternSyntaxException e){
//            //There is nothing to do here
//        }

//        for (int i = 0; i < searchList.size(); i++) {
//            txtEditor.replaceText(searchList.get(i).startingIndex,searchList.get(i).endIndex,txtReplace.getText());
//            searchMatches(txtSearch1.getText());
//            i=-1;
//        }

       while (!searchList.isEmpty()) {
           txtEditor.replaceText(searchList.get(0).startingIndex, searchList.get(0).endIndex, txtReplace.getText());
           searchMatches(txtSearch1.getText());
       }
    }

    //Regular Inner Class
    /*private class TextListener implements ChangeListener<String>{

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            FXUtil.highlightOnTextArea(txtEditor, newValue, Color.web("yellow",0.8));

            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(), matcher.end()));
                }
            }catch (PatternSyntaxException e){

            }
        }
    }*/
    //Static Nested Class
    /*private static class TextListener implements ChangeListener<String>{

        private TextArea txtEditor;
        private List<Index> searchList;

        public TextListener(TextArea txtEditor, List<Index>searchList){
            this.txtEditor=txtEditor;
            this.searchList=searchList;
        }

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            FXUtil.highlightOnTextArea(txtEditor, newValue, Color.web("yellow",0.8));

            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(), matcher.end()));
                }
            }catch (PatternSyntaxException e){

            }
        }
    }*/
}

class Index {
    int startingIndex;
    int endIndex;

    public Index(int startingIndex, int endIndex) {
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }
}

//Top Level class

/*class TextListener implements ChangeListener<String>{

        private TextArea txtEditor;
        private List<Index> searchList;

        public TextListener(TextArea txtEditor, List<Index>searchList){
            this.txtEditor=txtEditor;
            this.searchList=searchList;
        }

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            FXUtil.highlightOnTextArea(txtEditor, newValue, Color.web("yellow",0.8));

            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(), matcher.end()));
                }
            }catch (PatternSyntaxException e){

            }
        }
}*/
