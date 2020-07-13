package com.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.company.Controller.buttons;
import static javax.xml.bind.DatatypeConverter.parseDouble;

public class Mortgage {
    public static void mortgage(Stage primaryStage, int btnTxtWidth, int btnTxtHeight,int verticalAlign, int horizontalAlign){

        Stage mortgageStage = Controller.stagesShow("Mortgage");

//  -----------------------------Buttons and Labels - Mortgage-------------------------------
//        Declare every button which is in the Mortgage stage

        Button btnBack = buttons("Back",60, btnTxtHeight,0, 0);
        Button btnHelp = buttons("Help",65, btnTxtHeight,1008, 0);
        Button btnHistory = buttons("History",76, btnTxtHeight,937, 0);
        btnBack.setId("top");
        btnHelp.setId("top");
        btnHistory.setId("top");

        Label lblHead = Controller.labels("Mortgage", 450, 50);
        lblHead.setId("heading");

        Label lblA = Controller.labels("Home Price", verticalAlign*1.1,
                horizontalAlign);
        Label lbl$1 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                horizontalAlign+39);
        lbl$1.setId("signs");
        TextField txtA = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, horizontalAlign+32);

        Label lblDwnPmt = Controller.labels("Down Payment", verticalAlign*1.1,
                horizontalAlign+(90));
        Label lbl$2 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+90)+39);
        lbl$2.setId("signs");
        TextField txtDwnPmt = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+90)+32);

        Label lblT = Controller.labels("Loan Term",  verticalAlign*1.1,
                horizontalAlign+(90*2));
        Label lbl$3 = Controller.labels("Y", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+(90*2))+39);
        lbl$3.setId("signs");
        TextField txtT = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+(90*2))+32);

        Label lblR = Controller.labels("Interest Rate", verticalAlign*1.1,
                horizontalAlign+(90*3));
        Label lblPercentage = Controller.labels("%", verticalAlign*1.1+133,
                (horizontalAlign+(90*3))+39);
        lblPercentage.setId("signs");
        TextField txtR = Controller.txtField(btnTxtWidth-32, btnTxtHeight,
                verticalAlign, (horizontalAlign+(90*3))+32);

        Label lblPMT = Controller.labels("PMT",  verticalAlign*1.1,
                horizontalAlign+90*4);
        Label lbl$4 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+(90*4))+39);
        lbl$4.setId("signs");
        TextField txtPMT = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+90*4)+32);

        Button btnCalculate = buttons("Calculate",btnTxtWidth*1.5, btnTxtHeight*1.5,
                verticalAlign*4.5,(horizontalAlign+(90*4))+20);
        btnCalculate.setId("btnCalculate");

        Label lblHistory = Controller.labels("", 750,0);
        ScrollPane historyScrollPane = Controller.scrollPane(400,333,
                verticalAlign*9,horizontalAlign, lblHistory);
        historyScrollPane.setId("historyScrollPane");
        lblHistory.setId("lblHistory");

//      adding all text field to and array
        TextField[] txtArr = {txtA,txtDwnPmt,txtT,txtR,txtPMT};


//              -------------------------------------NumberPad---------------------------------------
        GridPane numberPad  = NumberPad.numberPad(
                verticalAlign*4.5, horizontalAlign+20, false
        );

//        when each text field is clicked - calling displayNumber method from NumberPad class
        for (TextField txt : txtArr){
            txt.setOnMouseClicked(event -> NumberPad.displayNumber(txt));
        }
//                -----------------------------------------------------------------------------------

//  -------------------------------------------------------------------------------------------------


//      -------------------------Calculation of Mortgage------------------------------
        /*
         Calculate the selected parameter from given values by user and all of these values stored in
         a temporary text file (append off - save only last entered values) and append into an array to
         show the history in a ScrollPane. History will write in a text file (append true - all history
         is visible there with time and date). By default set last entered values to textfield from
         temporary file.
         */

        DecimalFormat decimalFormatter = new DecimalFormat("#0.00");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//           -------------------setting old data to text fields-------------------
//      setting old data to text fields when opening program for first time
//      if it is very first time, it will set empty text fields.
      try {
            String line = Controller.readFile("Mortgage.txt");
            String[] oldDataSet = line.split(" ");
//            if app is not opening for the very first time, by default last entered data will set to the fields.
            if (oldDataSet.length == txtArr.length){
                for (int i = 0; i < txtArr.length; i++) {
                    txtArr[i].setText(oldDataSet[i]);
                }
            }
        } catch (FileNotFoundException e) {
          //if file not found - popping up a warning box that text file not found
          Controller.warningBox("Files not Found!","Navigate to ../Coursework1/tempFiles/" +
                  " see whether corresponding calculator text file is there");
        }

//        Store history data temporary in an array
        ArrayList<String> arrayHistory = new ArrayList<>();

        //        Calculation
        btnCalculate.setOnAction(event -> {
            Date date = new Date();
            double t = 0;
            double pmt = 0;
            double a = 0;
            double n = 12;
            double r = 0;
            double dwnPmt = 0;
            boolean flag = false;
            if (txtA.getText().isEmpty()) {
                try {
                    pmt = parseDouble(txtPMT.getText());
                    r = parseDouble(txtR.getText());
                    t = parseDouble(txtT.getText());
                    dwnPmt = parseDouble(txtDwnPmt.getText());
//                    a = dwnPmt+ ((n*pmt* (Math.pow((1 +(r/n)), (n * t)) - 1)) / (r * Math.pow((1 +(r/n)), (n*t))));
                    a = dwnPmt + ((n * pmt * (Math.pow((1 + (r / n)), (n * t)) - 1)) / (r * Math.pow((1 + (r / n)), (n * t))));
                    txtA.setText(String.valueOf(decimalFormatter.format(a)));
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not fill fields with Strings!");
                }
            } else if (txtPMT.getText().isEmpty()) {
                try {
                    t = parseDouble(txtT.getText());
                    dwnPmt = parseDouble(txtDwnPmt.getText());
                    r = parseDouble(txtR.getText());
                    a = parseDouble(txtA.getText());
                    pmt = ((a - dwnPmt) * (r / n) * Math.pow((1 + (r / n)), n * t)) / (Math.pow((1 + (r / n)), n * t) - 1);
                    txtPMT.setText(String.valueOf(decimalFormatter.format(pmt)));
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not fill fields with Strings!");
                }
            } else if (txtDwnPmt.getText().isEmpty()) {
                try {
                    t = parseDouble(txtT.getText());
                    r = parseDouble(txtR.getText());
                    a = parseDouble(txtA.getText());
                    pmt = parseDouble(txtPMT.getText());
                    dwnPmt = a - ((n * pmt * (Math.pow((1 + (r / n)), n * t) - 1)) / (r * Math.pow((1 + (r / n)), n * t)));
                    txtDwnPmt.setText(String.valueOf(decimalFormatter.format(dwnPmt)));
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not fill fields with Strings!");
                }
            } else if (txtT.getText().isEmpty()) {
                try {
                    pmt = parseDouble(txtPMT.getText());
                    r = parseDouble(txtR.getText());
                    a = parseDouble(txtA.getText());
                    dwnPmt = parseDouble(txtDwnPmt.getText());
//                    t = (Math.log((pmt / (pmt - ((r/n) * (a - dwnPmt)))))) /  (n * Math.log(1 + (r/n)));
                    t = (Math.log((pmt / (pmt - ((r/n) * (a - dwnPmt)))))) /  (n * Math.log(1 + (r/n)));
                    if ("�".equals(decimalFormatter.format(t))){
                        Controller.warningBox("Please enter reasonable values",
                                "Can not calculate Time for the values you entered.");
                    } else {
                        txtT.setText(String.valueOf(decimalFormatter.format(t)));
                        flag = true;
                    }
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not fill fields with Strings!");
                }
            } else {
                Controller.warningBox("Please keep one field empty",
                        "Keep only one field empty which you want to find the value empty");
            }

//              -----------------------------------------History Scroll Pane----------------------------------
            if (flag) {
                String history =
                        "\t---------------- " + dateFormatter.format(date) + " -----------------" + "\n\t"
                                + "Home Price : " + decimalFormatter.format(a) + " $\n\t" +
                                "Down Payment : " + decimalFormatter.format(dwnPmt) + " $\n\t" +
                                "Loan Term : " + decimalFormatter.format(t) + " years\n\t" +
                                "Interest Rate : " + decimalFormatter.format(r) + "%" + "\n\t" +
                                "PMT : " + decimalFormatter.format(pmt) + " $\n\t" +
                                "---------------------------------------------------------" + "\n\n";
                try {
                    Controller.writeFile("Output History/Mortgage.txt", history, true);
                    Controller.writeFile(
                            "tempFiles/Mortgage.txt",
                            decimalFormatter.format(a) + " " + decimalFormatter.format(dwnPmt) + " " +
                                    decimalFormatter.format(t) + " " + decimalFormatter.format(r) + " " +
                                    decimalFormatter.format(pmt),
                            false
                    );
                } catch (IOException e) {
                    Controller.warningBox("TextFile not found!", "Please check ..Coursework1/" +
                            "empFiles/Interest Savings.txt is there. If not create a text file " +
                            "Mortgage.txt");
                }
                arrayHistory.add(history);
                lblHistory.setText(String.valueOf(arrayHistory)
                        .replace("[","")
                        .replace("]","")
                        .replace(",","")
                );
            }
//               ----------------------------------------------------------------------------------------------
        });
//          ---------------------------------------------------------------------
//      -----------------------------------------------------------------------------------



//      -----------------------------------------Help View----------------------------------
        btnHelp.setOnAction(event -> {
            Stage helpViewStage = Controller.stagesShow("Help");

            Label lblHelpViewHeading =  Controller.labels("Help View",340,25);
            lblHelpViewHeading.setId("HelpViewHeading");

            GridPane helpViewGP = Controller.gridPane(44,150,730,169);
            helpViewGP.getColumnConstraints().add(new ColumnConstraints((200)));
            helpViewGP.getColumnConstraints().add(new ColumnConstraints((730-200)));

            Label lblVariable = Controller.labels("Variables",44,100);
            lblVariable.setId("subHeading");

            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));

            helpViewGP.add(new Label("Home Price"),0,0);
            helpViewGP.add(new Label("Price of the home"),1,0);
            helpViewGP.add(new Label("Down Payment"),0,1);
            helpViewGP.add(new Label("The amount that you pay at the beginning"),1,1);
            helpViewGP.add(new Label("Loan Term"),0,2);
            helpViewGP.add(new Label("The time money is invested or borrowed in years"),1,2);
            helpViewGP.add(new Label("Interest Rate"),0,3);
            helpViewGP.add(new Label("The annual interest rate"),1,3);
            helpViewGP.add(new Label("PMT"),0,4);
            helpViewGP.add(new Label("Monthly Payment"),1,4);

            Label lblHowTo = Controller.labels("How to calculate ?",44,425);
            lblHowTo.setId("subHeading");

            String howToDescription = "Empty the field you want to calculate. Example if you want to calculate" +
                    " Home Price keep that field empty and \nfill all the other values. Answer will display on the" +
                    "empty field and right side will show the history from the time \nyou start open the calculator." +
                    "If you want to see the whole history from the day start using this calculator, there \nis " +
                    "history button in top right corner. It will open the history in a text file.";
            Label lblHowDescription = Controller.labels(howToDescription,44,475);


            AnchorPane helpViewPane = new AnchorPane();
            Scene helpScene = new Scene(helpViewPane, 840,675);
            helpScene.getStylesheets().add("helpView.css");
            helpViewStage.setScene(helpScene);
            helpViewStage.setMinWidth(840);
            helpViewStage.setMinHeight(675);
            helpViewStage.setMaxWidth(840);
            helpViewStage.setMaxHeight(675);
            helpViewPane.getChildren().addAll(helpViewGP,lblHelpViewHeading,lblVariable,lblHowTo,lblHowDescription);
        });
//      -----------------------------------------------------------------------------------

//      ---------------------Back to primary Stage and open history txt files--------------------------------
        btnHistory.setOnAction(event -> {
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Output History/Mortgage.txt");
            try {
                pb.start();
            } catch (IOException e) {
                Controller.warningBox("Files not Found!","Navigate to ../Coursework1/" +
                        " see whether corresponding calculator text file is there. If not create text file " +
                        "Mortgage.txt"
                );
            }
        });

//        Back button
        Controller.stagesBack(btnBack, primaryStage);
//      -----------------------------------------------------------------------------------
//      Import a image file from FileInputStream and Set to background with an image view
        FileInputStream imageFile = null;
        try {
            imageFile = new FileInputStream("../Coursework1/Images/texture.png");
        } catch (FileNotFoundException e) {
            Controller.warningBox("Files not Found!","Navigate to ../Coursework1/Images" +
                    " see whether corresponding calculator images are there."
            );
        }
        Image backgroundImg = new Image(imageFile,1090,700,false,false);
        ImageView backgroundView = new ImageView(backgroundImg);
        backgroundView.setId("backgroundImg");

        AnchorPane mortgagePane = new AnchorPane();
        Scene mortgageScene = new Scene(mortgagePane,1090, 700);
        mortgageStage.setScene(mortgageScene);
        mortgageScene.getStylesheets().add("SecondaryStages.css");
        mortgageStage.setMinWidth(1090);
        mortgageStage.setMinHeight(700);
        mortgageStage.setMaxWidth(1090);
        mortgageStage.setMaxHeight(700);
        mortgagePane.getChildren().addAll(
                backgroundView,
                lblHead,lblA,lblT,lblR,lblPMT,lblDwnPmt,lblPercentage,lblHistory,
                txtA,txtT,txtR,txtPMT,txtDwnPmt,lbl$1,lbl$2,lbl$3,lbl$4,
                btnCalculate,btnBack,btnHelp,btnHistory,numberPad,historyScrollPane
        );
    }
}
