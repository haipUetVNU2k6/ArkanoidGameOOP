package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PauseCtrl {
    @FXML
    private void onResume(ActionEvent event) {
        System.out.println("resume Click");
        MainApp.stateStack.pop();
    }

    @FXML
    private void onSetting(ActionEvent event) {
        System.out.println("click on Setting");
    }

    @FXML
    private void onMenu(ActionEvent event) {
        MainApp.stateStack.pop();
        MainApp.stateStack.push(MainApp.menuState);
    }
}
