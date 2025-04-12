package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class CalculatorController {

    @FXML
    private Label displayLabel;

    private StringBuilder currentExpression = new StringBuilder();

    @FXML
    private void handleNumberInput(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String value = clickedButton.getText();

        // Avoid multiple decimals in a row
        if (value.equals(".") && currentExpression.length() > 0) {
            char lastChar = currentExpression.charAt(currentExpression.length() - 1);
            if (lastChar == '.') return;
        }

        currentExpression.append(value);
        displayLabel.setText(currentExpression.toString());
    }

    @FXML
    private void handleOperatorInput(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String operator = clickedButton.getText();

        if (currentExpression.length() > 0) {
            char lastChar = currentExpression.charAt(currentExpression.length() - 1);
            // Prevent multiple operators in a row
            if ("+-*/".indexOf(lastChar) != -1) {
                currentExpression.setCharAt(currentExpression.length() - 1, operator.charAt(0));
            } else {
                currentExpression.append(operator);
            }
            displayLabel.setText(currentExpression.toString());
        }
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        try {
            String expression = currentExpression.toString();
            double result = evaluateExpression(expression);
            displayLabel.setText(String.valueOf(result));
            currentExpression.setLength(0); // Clear expression for next input
        } catch (Exception e) {
            displayLabel.setText("Error");
            currentExpression.setLength(0);
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        currentExpression.setLength(0);
        displayLabel.setText("0");
    }

    @FXML
    private void handleBackspace(ActionEvent event) {
        int length = currentExpression.length();
        if (length > 0) {
            currentExpression.deleteCharAt(length - 1);
            displayLabel.setText(currentExpression.length() == 0 ? "0" : currentExpression.toString());
        }
    }

    // Basic expression evaluator (supports +, -, *, /)
    private double evaluateExpression(String expression) {
        // Replace with a proper parser for complex expressions if needed
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | number | `(` expression `)`

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected character: " + (char) ch);
                }

                return x;
            }
        }.parse();
    }
}
