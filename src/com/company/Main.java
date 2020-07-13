package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.company.Controller.buttons;

/*
* Name - Ramindu Walgama
* IIT ID - 2019730
* UOW ID - w1790183
*/


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
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


        /*
            Variables used in each stage

            A = Future Value (FV)
            N = Number of Periods (N)
            P = Principal Amount
            T Time / Loan term
            R = Interest Rate
            pmt = Monthly payment
            dwnPmt = DownPayment
        */


//        --------------------------------Interest Saving-------------------------------------
//        Open new stage for Interest Saving and close the primary stage.

        btnFV.setOnAction(event -> {
            Stage stage = (Stage) btnFV.getScene().getWindow();
            stage.close();
            InterestSaving.interestSavingStage(primaryStage, btnTxtWidth, btnTxtHeight,
                    verticalAlign, horizontalAlign);
        });

//        --------------------------------------Saving-------------------------------------
//        Open new stage for Saving and close the primary stage.

        btnSavings.setOnAction(event -> {
            Stage stage = (Stage) btnFV.getScene().getWindow();
            stage.close();
            Saving.savingStage(primaryStage, btnTxtWidth, btnTxtHeight,
                    verticalAlign, horizontalAlign);
        });

//        --------------------------------Loan-------------------------------------
//        Open new stage for Loan and close the primary stage.

        btnLoans.setOnAction(event -> {
            Stage stage = (Stage) btnLoans.getScene().getWindow();
            stage.close();
            Loan.loan(primaryStage, btnTxtWidth, btnTxtHeight, verticalAlign, horizontalAlign);
        });

//        --------------------------------Mortgage-------------------------------------
//        Open new stage for Mortgage and close the primary stage.

        btnMortgage.setOnAction(event -> {
            Stage stage = (Stage) btnMortgage.getScene().getWindow();
            stage.close();
            Mortgage.mortgage(primaryStage, btnTxtWidth, btnTxtHeight, verticalAlign, horizontalAlign);
        });

//      quit button will close the app
        btnQuit.setOnAction(event -> {
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            stage.close();
        });

//      Import a image file from FileInputStream and Set to background with an image view
        FileInputStream imageFile = new FileInputStream("../Coursework1/Images/MainBackgroundPS2.jpg");
        Image backgroundImg = new Image(imageFile);
        ImageView backgroundView = new ImageView(backgroundImg);

        AnchorPane root = new AnchorPane();
        Scene sceneMain = new Scene(root,800,500);
        sceneMain.getStylesheets().add("sceneMain.css");
        root.getChildren().addAll(backgroundView,btnFV,btnSavings,btnLoans,btnMortgage,btnQuit);
        primaryStage.setScene(sceneMain);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(500);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}