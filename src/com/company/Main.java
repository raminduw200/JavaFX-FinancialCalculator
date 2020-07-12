package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.company.Controller.buttons;

/*
* Name - Ramindu Walgama
* IIT ID - 2019730
* UOW ID - w1790183
*/


public class Main extends Application {


    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Finance Calculator");


//        -----------------------------Primary Stage Buttons------------------------------------------
        int verticalAlignment = 60;
        int horizontalAlignment = 70;
        int btnTxtWidth = 163;
        int btnTxtHeight = 40;
        Button btnFV = buttons("Fixed Deposit", btnTxtWidth,
                btnTxtHeight, verticalAlignment, horizontalAlignment);
        Button btnSavings = buttons("Savings", btnTxtWidth,
                btnTxtHeight, verticalAlignment, horizontalAlignment*2);
        Button btnLoans = buttons("Loans", btnTxtWidth,
                btnTxtHeight, verticalAlignment, horizontalAlignment*3);
        Button btnMortgage = buttons("Mortgage", btnTxtWidth,
                btnTxtHeight, verticalAlignment, horizontalAlignment*4);
        Button btnQuit = buttons("Quit", btnTxtWidth,
                btnTxtHeight, verticalAlignment, horizontalAlignment*5);

        int verticalAlign = 65;
        int horizontalAlign = 150;

//        --------------------------------Interest Saving-------------------------------------
//        Open new stage for Interest Saving - Functionality of the button Interest Saving in primaryStage

        btnFV.setOnAction(event -> {
            Stage stage = (Stage) btnFV.getScene().getWindow();
            stage.close();
            InterestSaving.interestSavingStage(primaryStage, btnTxtWidth, btnTxtHeight,
                    verticalAlign, horizontalAlign);
        });

//        --------------------------------------Saving-------------------------------------
//        Open new stage for Saving - Functionality of the button Saving in primaryStage

        btnSavings.setOnAction(event -> {
            Stage stage = (Stage) btnFV.getScene().getWindow();
            stage.close();
            Saving.savingStage(primaryStage, btnTxtWidth, btnTxtHeight,
                    verticalAlign, horizontalAlign);
        });

//        --------------------------------Loan-------------------------------------
//        Open new stage for Loan - Functionality of the button Loan in primaryStage

        btnLoans.setOnAction(event -> {
            Stage stage = (Stage) btnLoans.getScene().getWindow();
            stage.close();
            Loan.loan(primaryStage, btnTxtWidth, btnTxtHeight, verticalAlign, horizontalAlign);
        });

//        --------------------------------Mortgage-------------------------------------
//        Open new stage for Mortgage - Functionality of the button Mortgage in primaryStage

        btnMortgage.setOnAction(event -> {
            Stage stage = (Stage) btnMortgage.getScene().getWindow();
            stage.close();
            Mortgage.mortgage(primaryStage, btnTxtWidth, btnTxtHeight, verticalAlign, horizontalAlign);
        });


        btnQuit.setOnAction(event -> {
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            stage.close();
        });

        AnchorPane root = new AnchorPane();
        root.setId("root");
        Scene sceneMain = new Scene(root,600,500);
        sceneMain.getStylesheets().add("sceneMain.css");
        root.getChildren().addAll(btnFV,btnSavings,btnLoans,btnMortgage,btnQuit);
        primaryStage.setScene(sceneMain);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxWidth(600);
        primaryStage.setMaxHeight(500);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}