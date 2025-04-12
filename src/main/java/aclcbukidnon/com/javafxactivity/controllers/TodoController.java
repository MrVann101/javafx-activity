package aclcbukidnon.com.javafxactivity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.util.Optional;

public class TodoController {

    @FXML
    private ListView<String> todoList;

    private ObservableList<String> items;

    @FXML
    public void initialize() {
        items = FXCollections.observableArrayList();
        todoList.setItems(items);
    }

    @FXML
    private void onCreateClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Todo");
        dialog.setHeaderText("Create a new to-do item");
        dialog.setContentText("Enter task:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(task -> {
            if (!task.trim().isEmpty()) {
                items.add(task.trim());
            } else {
                showWarning("Task cannot be empty.");
            }
        });
    }

    @FXML
    private void onDeleteClick(ActionEvent event) {
        int selectedIndex = todoList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            items.remove(selectedIndex);
        } else {
            showWarning("Please select a task to delete.");
        }
    }

    @FXML
    private void onListEdit() {
        // Optional: Implement if editable ListView is needed
    }

    private void showWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
