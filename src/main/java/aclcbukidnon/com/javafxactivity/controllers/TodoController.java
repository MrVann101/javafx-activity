package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class TodoController {

    @FXML
    private ListView<CheckBox> todoList;

    private final Path savePath = Paths.get("todo.txt");

    @FXML
    public void initialize() {
        loadTasks();
    }

    @FXML
    public void onCreateClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Task");
        dialog.setHeaderText("Enter a new task:");
        dialog.setContentText("Task:");

        dialog.showAndWait().ifPresent(task -> {
            CheckBox newItem = new CheckBox(task);
            todoList.getItems().add(newItem);
            saveTasks();
        });
    }

    @FXML
    public void onDeleteClick() {
        var selected = todoList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            todoList.getItems().remove(selected);
            saveTasks();
        }
    }

    @FXML
    public void onListEdit() {
        // Optional: implement future edit behavior
    }

    private void saveTasks() {
        try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
            for (CheckBox cb : todoList.getItems()) {
                writer.write(cb.isSelected() + "|" + cb.getText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        if (!Files.exists(savePath)) return;

        try (BufferedReader reader = Files.newBufferedReader(savePath)) {
            todoList.getItems().clear();
            reader.lines().forEach(line -> {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    CheckBox cb = new CheckBox(parts[1]);
                    cb.setSelected(Boolean.parseBoolean(parts[0]));
                    cb.selectedProperty().addListener((obs, oldVal, newVal) -> saveTasks());
                    todoList.getItems().add(cb);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
