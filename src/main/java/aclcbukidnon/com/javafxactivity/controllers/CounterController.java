package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterController {

        @FXML
        private Label numberLabel;

        private int count = 0;

        @FXML
        private void onClickAdd() {
            count++;
            updateLabel();
        }

        @FXML
        private void onClickMinus() {
            count--;
            updateLabel();
        }

        private void updateLabel() {
            numberLabel.setText(String.valueOf(count));
        }
    }

