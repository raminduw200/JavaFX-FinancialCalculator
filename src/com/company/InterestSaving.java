package com.company;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.company.Controller.buttons;
import static javax.xml.bind.DatatypeConverter.parseDouble;

public class InterestSaving {
    public static void interestSavingStage(Stage primaryStage, int btnTxtWidth, int btnTxtHeight,int verticalAlign, int horizontalAlign){

        Stage iSStage = Controller.stagesShow("Interest Savings (Fixed Deposit)");

//  -----------------------------Buttons and Labels - Interest Saving-------------------------------
//        Declare every button which is in the interest saving stage

        Button btnBack = buttons("Back",60, btnTxtHeight,0, 0);
        Button btnHelp = buttons("Help",65, btnTxtHeight,1008, 0);
        Button btnHistory = buttons("History",76, btnTxtHeight,937, 0);
        btnBack.setId("top");
        btnHelp.setId("top");
        btnHistory.setId("top");

        Label lblHead = Controller.labels("Interest  Savings  (Fixed  Deposit)", 240, 50);
        lblHead.setId("heading");

        Label lblA = Controller.labels("Future Value (FV)", verticalAlign*1.1,
                horizontalAlign);
        Label lbl$1 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                horizontalAlign+39);
        lbl$1.setId("signs");
        TextField txtA = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, horizontalAlign+32);

        Label lblP = Controller.labels("Principal Amount", verticalAlign*1.1,
                horizontalAlign+(90));
        Label lbl$2 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+90)+39);
        lbl$2.setId("signs");
        TextField txtP = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+90)+32);

        Label lblT = Controller.labels("Time", verticalAlign*1.1,
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

        Button btnISCalculate = buttons("Calculate",850, btnTxtHeight*1.5,
                verticalAlign+32, horizontalAlign+(90*4.5));
        btnISCalculate.setId("btnCalculate");

        Label lblHistory = Controller.labels("", 750,0);
        ScrollPane historyScrollPane = Controller.scrollPane(400,333,
                verticalAlign*9,horizontalAlign, lblHistory);
        historyScrollPane.setId("historyScrollPane");
        lblHistory.setId("lblHistory");

//      adding all text field to and array
        TextField[] txtArr = {txtA,txtP,txtT,txtR};

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


//      -------------------------Calculation of interest rate------------------------------
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
            String line = Controller.readFile("Interest Savings.txt");
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
        btnISCalculate.setOnAction(event -> {
            Date date = new Date();
            double n = 12;
            double p = 0;
            double t = 0;
            double a = 0;
            double r = 0;
            boolean flag = false;
            if (txtA.getText().isEmpty()) {
                try {
                    p = parseDouble(txtP.getText());
                    t = parseDouble(txtT.getText());
                    r = parseDouble(txtR.getText())/100;
                    a = p*(Math.pow((1+(r/n)),n*t));
                    txtA.setText(String.valueOf(decimalFormatter.format(a)));
                    r*=100;
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else if (txtP.getText().isEmpty()) {
                try {
                    r = parseDouble(txtR.getText())/100;
                    t = parseDouble(txtT.getText());
                    a = parseDouble(txtA.getText());
                    p = a/ (Math.pow((1+(r/n)),n*t));
                    txtP.setText(String.valueOf(decimalFormatter.format(p)));
                    r*=100;
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else if (txtT.getText().isEmpty()) {
                try {
                    p = parseDouble(txtP.getText());
                    r = parseDouble(txtR.getText())/100;
                    a = parseDouble(txtA.getText());
                    t = Math.log(a/p)/(n*(Math.log(1+(r/n))));
                    txtT.setText(String.valueOf(decimalFormatter.format(t)));
                    r*=100;
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else if (txtR.getText().isEmpty()) {
                try {
                    p = parseDouble(txtP.getText());
                    t = parseDouble(txtT.getText());
                    a = parseDouble(txtA.getText());
                    r = n * (Math.pow(a / p, (1 / (n * t))) - 1)*100;
                    txtR.setText(String.valueOf(decimalFormatter.format(r)));
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else {
                Controller.warningBox("Please keep one field empty",
                        "Keep only one empty field which you want to find the value " +
                                "(Example : If you want to find interest rate, keep that field empty");
            }

//              -----------------------------------------History Scroll Pane----------------------------------
            if (flag) {
                String history =
                        "\t---------------- " + dateFormatter.format(date) + " -----------------" + "\n\t"
                                + "Future Value : " + decimalFormatter.format(a) + " $\n\t" +
                                "Number of Periods (N) : " + n + " months\n\t" +
                                "Principal Amount : " + decimalFormatter.format(p) + " $\n\t" +
                                "Time : " + decimalFormatter.format(t) + " years\n\t" +
                                "Interest Rate : " + decimalFormatter.format(r) + "%" + "\n\t" +
                                "---------------------------------------------------------" + "\n\n";
                try {
                    Controller.writeFile("Output History/Interest Savings.txt", history, true);
                    Controller.writeFile(
                            "tempFiles/Interest Savings.txt",
                            decimalFormatter.format(a) + " " + decimalFormatter.format(p) + " " +
                                    decimalFormatter.format(t) + " " + decimalFormatter.format(r),
                            false
                    );
                } catch (IOException e) {
                    e.printStackTrace();
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

            GridPane helpViewGP = Controller.gridPane(44,150,750,169);
            helpViewGP.getColumnConstraints().add(new ColumnConstraints((153)));
            helpViewGP.getColumnConstraints().add(new ColumnConstraints((750-153)));

            Label lblVariable = Controller.labels("Variables",44,100);
            lblVariable.setId("subHeading");

            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));
            helpViewGP.getRowConstraints().add(new RowConstraints(40));

            helpViewGP.add(new Label("Future Value(FV)"),0,0);
            helpViewGP.add(new Label("The Future value of the investment/loan" +
                    ", including interest"),1,0);
            helpViewGP.add(new Label("Principal Amount"),0,1);
            helpViewGP.add(new Label("The principal investment amount ( the initial " +
                    "deposit or loan amount - present value )"),1,1);
            helpViewGP.add(new Label("Time"),0,2);
            helpViewGP.add(new Label("The time money is invested or borrowed" +
                    " in years"),1,2);
            helpViewGP.add(new Label("Interest Rate"),0,3);
            helpViewGP.add(new Label("The annual interest rate "),1,3);

            Label lblHowTo = Controller.labels("How to calculate ?",44,370);
            lblHowTo.setId("subHeading");

            String howToDescription = "Empty the field you want to calculate. Example if you want to calculate" +
                    " Future Value keep that field empty and \nfill all the other values. Answer will display on the" +
                    "empty field and right side will show the history from the time \nyou start open the calculator." +
                    "If you want to see the whole history from the day start using this calculator, there \nis " +
                    "history button in top right corner. It will open the history in a text file.";
            Label lblHowDescription = Controller.labels(howToDescription,44,420);


            AnchorPane helpViewPane = new AnchorPane();
            Scene helpScene = new Scene(helpViewPane, 830,605);
            helpScene.getStylesheets().add("helpView.css");
            helpViewStage.setScene(helpScene);
            helpViewStage.setMinWidth(830);
            helpViewStage.setMinHeight(605);
            helpViewStage.setMaxWidth(830);
            helpViewStage.setMaxHeight(605);
            helpViewPane.getChildren().addAll(helpViewGP,lblHelpViewHeading,lblVariable,lblHowTo,lblHowDescription);
        });
//      -----------------------------------------------------------------------------------

//      -------------------------Back to primary Stage and open history txt files-------------------------------------
        btnHistory.setOnAction(event -> {
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Output History/Interest Savings.txt");
            try {
                pb.start();
            } catch (IOException e) {
                Controller.warningBox("Files not Found!","Navigate to ../Coursework1/" +
                        " see whether corresponding calculator text file is there");
            }
        });

//        Back button
        Controller.stagesBack(btnBack, primaryStage);
//      -----------------------------------------------------------------------------------

        AnchorPane iSPane = new AnchorPane();
        iSPane.setId("iSPane");
        Scene iSScene = new Scene(iSPane,1090, 700);
        iSScene.getStylesheets().add("SecondaryStages.css");
        iSStage.setScene(iSScene);
        iSStage.setMinWidth(1090);
        iSStage.setMinHeight(700);
        iSStage.setMaxWidth(1090);
        iSStage.setMaxHeight(700);
        iSPane.getChildren().addAll(
                lblHead,lblA,lblT,lblP,lblR,lblPercentage,lblHistory,
                txtA,txtP,txtT,txtR,lbl$1,lbl$2,lbl$3,
                btnISCalculate,btnBack,btnHelp,btnHistory,numberPad,historyScrollPane
        );
    }
}