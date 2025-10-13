package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PauseCtrl {
    @FXML
    private void clickResume(ActionEvent event) {
        MainApp.stateStack.pop();
    }

    @FXML
    private void clickSetting(ActionEvent event) {
        System.out.println("click on Setting");
    }

    @FXML
    private void clickMenu(ActionEvent event) {
        MainApp.stateStack.pop();
        MainApp.stateStack.push(MainApp.menuState);
    }
}
