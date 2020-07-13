package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import static com.company.Controller.buttons;

public class NumberPad {

    static private final int btnSize = 80;
//    Declare an array for buttons.
    static private final Button[] keys = new Button[14];
    public static GridPane numberPad(double layoutX, double layoutY, boolean showMinus){
//        Declare buttons and add to buttons array.
        for (int i = 0; i < 10 ; i++){
            keys[i] = buttons(String.valueOf(i) ,btnSize,btnSize,0,0);
        }
        keys[10] = buttons("Delete",btnSize,btnSize,0,0);
        keys[11] = buttons(".",btnSize,btnSize,0,0);
        keys[12] = buttons("Clear",btnSize,btnSize,0,0);
        keys[13] = buttons("-",btnSize,btnSize,0,0);

//        Setting grid pane gaps between numberpad keys
        GridPane keyboard =  new GridPane();
        keyboard.setHgap(7);
        keyboard.setVgap(7);

//            Make all keys Focus Transferable
        for(Button btn : keys){
            btn.setFocusTraversable(false);
        }

//        Showing minus key only in selected calculators
        keys[13].setVisible(showMinus);

//        Positioning keys in grid pane
        GridPane.setConstraints(keys[1], 0,0);
        GridPane.setConstraints(keys[2], 1,0);
        GridPane.setConstraints(keys[3], 2,0);

        GridPane.setConstraints(keys[4], 0,1);
        GridPane.setConstraints(keys[5], 1,1);
        GridPane.setConstraints(keys[6], 2,1);

        GridPane.setConstraints(keys[7], 0,2);
        GridPane.setConstraints(keys[8], 1,2);
        GridPane.setConstraints(keys[9], 2,2);

        GridPane.setConstraints(keys[11], 0,3);
        GridPane.setConstraints(keys[0], 1,3);
        GridPane.setConstraints(keys[10], 2,3);

        GridPane.setConstraints(keys[12], 2,4);
        GridPane.setConstraints(keys[13], 0,4);

//        Setting layouts and size of the window
        keyboard.setLayoutY(layoutY);
        keyboard.setLayoutX(layoutX);
        keyboard.setPrefSize(250,300);
        keyboard.setMinSize (Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        keyboard.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        keyboard.getChildren().addAll(keys);

        return keyboard;
    }

    public static void displayNumber(TextField txt){
//        Functionality of each key.
//        Appending character for passed parameter (txt) when button is clicked.
        for (Button btn : keys) {
            if (btn.getText().equals("Delete")) {
                btn.setOnAction(event -> txt.setText(txt.getText().substring(0, txt.getText().length() - 1)));
            } else if (btn.getText().equals("Clear")) {
                btn.setOnAction(event -> txt.setText(""));
            } else if (btn.getText().equals("-")) {
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!txt.getText().contains("-")) {
                            txt.setText("-" + txt.getText());
                        }
                    }
                });
            } else if (btn.getText().equals(".")) {
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!txt.getText().contains(".")) {
                            txt.appendText(btn.getText());
                        }
                    }
                });
            } else {
                btn.setOnAction(event -> txt.appendText(btn.getText()));
            }
        }
    }
}
