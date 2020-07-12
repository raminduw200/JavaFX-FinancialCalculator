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

public class Loan {
    public static void loan(Stage primaryStage, int btnTxtWidth, int btnTxtHeight,int verticalAlign, int horizontalAlign){

        Stage loanStage = Controller.stagesShow("Loan");

//  -----------------------------Buttons and Labels - Loan-------------------------------
//        Declare every button which is in the interest saving stage

        Button btnBack = buttons("Back",60, btnTxtHeight,0, 0);
        Button btnHelp = buttons("Help",65, btnTxtHeight,1008, 0);
        Button btnHistory = buttons("History",76, btnTxtHeight,937, 0);
        btnBack.setId("top");
        btnHelp.setId("top");
        btnHistory.setId("top");

        Label lblHead = Controller.labels("Loan", 450, 50);
        lblHead.setId("heading");

        Label lblA = Controller.labels("Loan Amount", verticalAlign*1.1,
                horizontalAlign);
        Label lbl$1 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                horizontalAlign+39);
        lbl$1.setId("signs");
        TextField txtA = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, horizontalAlign+32);

        Label lblT = Controller.labels("Loan Term",verticalAlign*1.1,
                horizontalAlign+(90));
        Label lbl$2 = Controller.labels("Y", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+90)+39);
        lbl$2.setId("signs");
        TextField txtT = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+90)+32);

        Label lblR = Controller.labels("Interest Rate", verticalAlign*1.1,
                horizontalAlign+(90*2));
        Label lblPercentage = Controller.labels("%", verticalAlign*1.1+133,
                (horizontalAlign+(90*2))+39);
        lblPercentage.setId("signs");
        TextField txtR = Controller.txtField(btnTxtWidth-32, btnTxtHeight,
                verticalAlign, (horizontalAlign+(90*2))+32);

        Label lblPMT = Controller.labels("Monthly Pay",  verticalAlign*1.1,
                horizontalAlign+70*4);
        Label lbl$3 = Controller.labels("$", verticalAlign+btnTxtWidth-25,
                (horizontalAlign+(90*3))+48);
        lbl$3.setId("signs");
        TextField txtPMT = Controller.txtField(btnTxtWidth, btnTxtHeight,
                verticalAlign, (horizontalAlign+70*4)+32);

        Button btnCalculate = buttons("Calculate",850, btnTxtHeight*1.5,
                verticalAlign+32, horizontalAlign+(90*4.5));
        btnCalculate.setId("btnCalculate");

        Label lblHistory = Controller.labels("", 750,0);
        ScrollPane historyScrollPane = Controller.scrollPane(400,333,
                verticalAlign*9,horizontalAlign, lblHistory);
        lblHistory.setId("lblHistory");

//      adding all text field to and array
        TextField[] txtArr = {txtA,txtT,txtR,txtPMT};

//              -------------------------------------NumberPad---------------------------------------
        GridPane numberPad  = NumberPad.numberPad(
                verticalAlign*4.5, horizontalAlign+20, true
        );

        //        when each text field is clicked - calling displayNumber method from NumberPad class
        for (TextField txt : txtArr){
            txt.setOnMouseClicked(event -> NumberPad.displayNumber(txt));
        }
//                -----------------------------------------------------------------------------------

//  -------------------------------------------------------------------------------------------------


//      -------------------------Calculation of Loan------------------------------
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
            String line = Controller.readFile("Loan.txt");
            String[] oldDataSet = line.split(" ");
            //            if app is not opening for the very first time, by default last entered data will set to the fields.
            for (int i = 0; i < txtArr.length; i++){
                txtArr[i].setText(oldDataSet[i]);
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
            double r = 0;
            boolean flag = false;
            if (txtA.getText().isEmpty()) {
                try {
                    pmt = parseDouble(txtPMT.getText());
                    r = parseDouble(txtR.getText())/100;
                    t = parseDouble(txtT.getText());
//                    double x = Math.pow((1 + r / 12), 12 * t);
//                    a = (pmt * (x-1)) / ((r/12) * x);
                    a =  (pmt / r) * (1 - (1 / Math.pow((1 + r), t)));
                    txtA.setText(String.valueOf(decimalFormatter.format(a)));
                    r*=100;
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else if (txtPMT.getText().isEmpty()) {
                try {
                    t = parseDouble(txtT.getText());
                    r = parseDouble(txtR.getText())/100;
                    a = parseDouble(txtA.getText());
//                    double x = Math.pow((1 + r / 12), 12 * t);
//                    pmt = (a * (r/12) * x)/ (x-1);
                    pmt = (a * r) / (1 - (1 / Math.pow((1 + r), t)));
                    txtPMT.setText(String.valueOf(decimalFormatter.format(pmt)));
                    r*=100;
                    flag = true;
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else if (txtT.getText().isEmpty()) {
                try {
                    pmt = parseDouble(txtPMT.getText());
                    r = parseDouble(txtR.getText())/100;
                    a = parseDouble(txtA.getText());
//                    t = Math.log(((a*r) / (r+pmt)) + (pmt / (r+pmt)))/Math.log(r+1);
                    t = Math.log((pmt / r) / ((pmt / r) - a)) / Math.log(1 + r);
                    r*=100;
                    if ("�".equals(decimalFormatter.format(t))){
                        Controller.warningBox("Please enter reasonable values",
                                "Can not calculate Loan Term for the values you entered.");
                    } else {
                        txtT.setText(String.valueOf(decimalFormatter.format(t)));
                        flag = true;
                    }
                } catch (NumberFormatException e) {
                    Controller.warningBox("Please enter valid values",
                            "Only one field can be Empty. Do not filled fields with Strings!");
                }
            } else {
                Controller.warningBox("Please keep one field empty",
                        "Keep only one empty field which you want to find the value. " +
                                "Note : Interest rate can not calculate in this calculator");
            }

//              -----------------------------------------History Scroll Pane----------------------------------
            if (flag) {
                String history =
                        "\t---------------- " + dateFormatter.format(date) + " -----------------" + "\n\t"
                                + "Loan Amount : " + decimalFormatter.format(a) + " $\n\t" +
                                "Loan Term : " + decimalFormatter.format(t) + " years\n\t" +
                                "Interest Rate : " + decimalFormatter.format(r) + "%" + "\n\t" +
                                "Monthly Pay : " + decimalFormatter.format(pmt) + " $\n\t" +
                                "---------------------------------------------------------" + "\n\n";
                try {
                    Controller.writeFile("Output History/Loan.txt", history, true);
                    Controller.writeFile(
                            "tempFiles/Loan.txt",
                            decimalFormatter.format(a) + " " + decimalFormatter.format(t) + " " +
                                    decimalFormatter.format(r) + " " + decimalFormatter.format(pmt),
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

            helpViewGP.add(new Label("Loan Amount"),0,0);
            helpViewGP.add(new Label("Amount of the loan that need to pay."),1,0);
            helpViewGP.add(new Label("Loan Term"),0,1);
            helpViewGP.add(new Label("The time money is invested or borrowed in years"),1,1);
            helpViewGP.add(new Label("Interest Rate"),0,2);
            helpViewGP.add(new Label("The annual interest rate "),1,2);
            helpViewGP.add(new Label("Monthly Payment"),0,3);
            helpViewGP.add(new Label("How muc that need to pay monthly"),1,3);

            Label lblHowTo = Controller.labels("How to calculate ?",44,370);
            lblHowTo.setId("subHeading");

            String howToDescription = "Empty the field you want to calculate. Example if you want to calculate" +
                    " Loan Amount keep that field empty and \nfill all the other values. Answer will display on the" +
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
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Output History/Loan.txt");
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

        AnchorPane loanPane = new AnchorPane();
        Scene loanScene = new Scene(loanPane, 1090, 700);
        loanScene.getStylesheets().add("SecondaryStages.css");
        loanStage.setScene(loanScene);
        loanStage.setMinWidth(1090);
        loanStage.setMinHeight(700);
        loanStage.setMaxWidth(1090);
        loanStage.setMaxHeight(700);
        loanPane.getChildren().addAll(
                lblHead,lblA,lblT,lblR,lblPMT,lblPercentage,lblHistory,
                txtA,txtT,txtR,txtPMT,lbl$1,lbl$2,lbl$3,
                btnCalculate,btnBack,btnHelp,btnHistory,numberPad,historyScrollPane
        );
    }
}
