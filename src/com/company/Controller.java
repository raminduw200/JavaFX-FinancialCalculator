package com.company;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Controller {
    //  method to declare a button.
    public static Button buttons(String btnName, double btnWidth, double btnHeight, double layoutX, double layoutY) {
        Button btn = new Button(btnName);
        btn.setPrefSize(btnWidth,btnHeight);
        btn.setLayoutX(layoutX);
        btn.setLayoutY(layoutY);
        return btn;
    }

    //  method to declare a label.
    public static Label labels(String lblName, double layoutX, double layoutY) {
        Label lbl = new Label(lblName);
        lbl.setLayoutX(layoutX);
        lbl.setLayoutY(layoutY);
        return lbl;
    }

    //  method to declare a textfield.
    public static TextField txtField(double txtWidth, double txtHeight, double layoutX, double layoutY) {
        TextField txt = new TextField();
        txt.setPrefSize(txtWidth,txtHeight);
        txt.setLayoutX(layoutX);
        txt.setLayoutY(layoutY);
        return txt;
    }

    //  method to declare a gridpane.
    public static GridPane gridPane(double layoutX,double layoutY, double width,double height){
        GridPane gp = new GridPane();
        gp.setLayoutX(layoutX);
        gp.setLayoutY(layoutY);
        gp.setPrefWidth(width);
        gp.setPrefHeight(height);
        gp.setMinSize (Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        gp.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return gp;
    }

    //  method to declare a stage and show.
    public static Stage stagesShow(String label) {
        Stage stageName = new Stage();
        stageName.setTitle(label);
        stageName.show();
        return stageName;
    }

    //  button action to close a window (stage) and open a new window.
    public static void stagesBack(Button btn, Stage newStage){
        btn.setOnAction(event -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
            newStage.show();
        });
    }

    //  method to declare a scrollPane.
    public static ScrollPane scrollPane(double scrollPaneWidth, double scrollPaneHeight,
                                        double layoutX, double layoutY, Label content){
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(scrollPaneWidth,scrollPaneHeight);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setLayoutX(layoutX);
        sp.setLayoutY(layoutY);
        sp.setContent(content);
        return sp;
    }

    //  method to declare a warning box with customised message - Alert box.
    public static void warningBox(String headerText,String contentText){
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setAlertType(Alert.AlertType.ERROR);
        alertBox.setHeaderText(headerText);
        alertBox.setContentText(contentText);
        alertBox.show();
    }

    //  method to write in a text file.
    public static void writeFile(String fileName, String historyContent, boolean appendValue) throws IOException {
    //  Write into a file - Character Stream
        File txtFile = new File("../Coursework1/"+fileName);
        FileWriter fw = null;
        PrintWriter pw = null;
        try{
            fw = new FileWriter(txtFile, appendValue);
            pw = new PrintWriter(fw, true);
            pw.print(historyContent);
        } catch (FileNotFoundException e) {
            //if file not found - popping up a warning box that text file not found
            warningBox("Files not Found!","Navigate to ../Coursework1/" +
                    " see whether corresponding calculator text file is there");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw.close();
            pw.close();
        }
    }

    //  method to read from a text file
    public static String readFile(String fileName) throws FileNotFoundException {
        File txtFile = new File("../Coursework1/tempFiles/"+fileName);
        Scanner sc = new Scanner(txtFile);
        return sc.nextLine();
    }
}
