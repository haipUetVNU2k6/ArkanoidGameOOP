//package com.example.arkanoidProject.state_controller.controller;
//
//import com.example.arkanoidProject.MainApp;
//import com.example.arkanoidProject.userAccount.User;
//import com.example.arkanoidProject.userAccount.UserManager;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//
//import java.util.Optional;
//
//public class ChangeAccountCtrl {
//
//    @FXML private ComboBox<String> userComboBox;
//    @FXML private Button switchButton;
//    @FXML private Button newButton;
//    @FXML private TextField newAccountTextField;
//
//    private UserManager userManager;
//
//    public ChangeAccountCtrl() {
//        // constructor mặc định
//    }
//
//    @FXML
//    public void initialize() {
//        userManager = MainApp.userManager;
//        refreshUsers();
//
//        switchButton.setOnAction(e -> switchAccount());
//        newButton.setOnAction(e -> createNewAccount());
//    }
//
//    private void refreshUsers() {
//        userComboBox.getItems().clear();
//        for (User u : userManager.getUsers()) {
//            userComboBox.getItems().add(u.getUsername());
//        }
//        if (userManager.getCurrentUser() != null) {
//            userComboBox.setValue(userManager.getCurrentUser().getUsername());
//        } else if (!userComboBox.getItems().isEmpty()) {
//            userComboBox.setValue(userComboBox.getItems().get(0));
//        }
//    }
//
//    private void switchAccount() {
//        String username = userComboBox.getValue();
//        if (username != null) {
//            Optional<User> selected = userManager.getUsers().stream()
//                    .filter(u -> u.getUsername().equals(username))
//                    .findFirst();
//            selected.ifPresent(user -> {
//                userManager.setCurrentUser(user);
//                userManager.saveUsers();
//                System.out.println("Switched to " + user.getUsername());
//            });
//        }
//    }
//
//    private void createNewAccount() {
//        String name = newAccountTextField.getText().trim();
//        if (name.isEmpty()) {
//            showAlert("Error", "Username cannot be empty!");
//            return;
//        }
//
//        boolean exists = userManager.getUsers().stream()
//                .anyMatch(u -> u.getUsername().equals(name));
//        if (exists) {
//            showAlert("Error", "Username already exists!");
//            return;
//        }
//
//        User newUser = new User(name);
//        userManager.addUser(newUser);
//        userManager.setCurrentUser(newUser);
//        userManager.saveUsers();
//        refreshUsers();
//        showAlert("Success", "Created and switched to new user: " + name);
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.ConfigRMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//
//    @FXML
//    private void onSwitch() {
//        switchAccount();
//    }
//
//    @FXML
//    private void onCreate() {
//        createNewAccount();
//    }
//}

package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.userAccount.User;
import com.example.arkanoidProject.userAccount.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Optional;

public class ChangeAccountCtrl {

    @FXML private ComboBox<String> userComboBox;
    @FXML private Button switchButton;
    @FXML private Button newButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private TextField newAccountTextField;
    @FXML private Label messageLabel;

    private UserManager userManager;

    @FXML
    public void initialize() {
        userManager = MainApp.userManager;
        refreshUsers();
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
        messageLabel.setText("");
    }

    @FXML
    private void onSwitch() {
        String username = userComboBox.getValue();
        if (username != null) {
            Optional<User> selected = userManager.getUsers().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
            selected.ifPresentOrElse(user -> {
                userManager.setCurrentUser(user);
                userManager.saveUsers();
                messageLabel.setText("Switched to " + user.getUsername());
            }, () -> messageLabel.setText("User not found!"));
        }
    }

    @FXML
    private void onCreate() {
        String name = newAccountTextField.getText().trim();
        if (name.isEmpty()) {
            messageLabel.setText("Username cannot be empty!");
            return;
        }
        boolean exists = userManager.getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(name));
        if (exists) {
            messageLabel.setText("Username already exists!");
            return;
        }
        User newUser = new User(name);
        userManager.addUser(newUser);
        userManager.setCurrentUser(newUser);
        userManager.saveUsers();
        refreshUsers();
        messageLabel.setText("Created and switched to new user: " + name);
    }

    @FXML
    private void onDelete() {
        String username = userComboBox.getValue();
        if (username == null) {
            messageLabel.setText("No user selected!");
            return;
        }
        userManager.getUsers().removeIf(u -> u.getUsername().equals(username));
        if (userManager.getCurrentUser() != null && userManager.getCurrentUser().getUsername().equals(username)) {
            userManager.setCurrentUser(null);
        }
        userManager.saveUsers();
        refreshUsers();
        messageLabel.setText("Deleted user: " + username);
    }

    @FXML
    private void onBack() {
        // Quay lại màn hình trước, ví dụ:
        MainApp.stateStack.pop();
    }
}
