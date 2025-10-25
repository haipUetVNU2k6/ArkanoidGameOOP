package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.userAccount.User;
import com.example.arkanoidProject.userAccount.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Optional;

public class ChangeAccountCtrl {

    @FXML private ComboBox<String> userComboBox;
    @FXML private Button switchButton;
    @FXML private Button newButton;
    @FXML private TextField newAccountTextField;

    private UserManager userManager;

    public ChangeAccountCtrl() {
        // constructor mặc định
    }

    @FXML
    public void initialize() {
        userManager = MainApp.userManager;
        refreshUsers();

        switchButton.setOnAction(e -> switchAccount());
        newButton.setOnAction(e -> createNewAccount());
    }

    private void refreshUsers() {
        userComboBox.getItems().clear();
        for (User u : userManager.getUsers()) {
            userComboBox.getItems().add(u.getUsername());
        }
        if (userManager.getCurrentUser() != null) {
            userComboBox.setValue(userManager.getCurrentUser().getUsername());
        } else if (!userComboBox.getItems().isEmpty()) {
            userComboBox.setValue(userComboBox.getItems().get(0));
        }
    }

    private void switchAccount() {
        String username = userComboBox.getValue();
        if (username != null) {
            Optional<User> selected = userManager.getUsers().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
            selected.ifPresent(user -> {
                userManager.setCurrentUser(user);
                userManager.saveUsers();
                System.out.println("Switched to " + user.getUsername());
            });
        }
    }

    private void createNewAccount() {
        String name = newAccountTextField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Username cannot be empty!");
            return;
        }

        boolean exists = userManager.getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(name));
        if (exists) {
            showAlert("Error", "Username already exists!");
            return;
        }

        User newUser = new User(name);
        userManager.addUser(newUser);
        userManager.setCurrentUser(newUser);
        userManager.saveUsers();
        refreshUsers();
        showAlert("Success", "Created and switched to new user: " + name);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void onSwitch() {
        switchAccount();
    }

    @FXML
    private void onCreate() {
        createNewAccount();
    }
}
