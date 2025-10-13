package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuCtrl {
    @FXML
    private void onPLay(ActionEvent event) {
        System.out.println("PLAY button clicked");
        MainApp.stateStack.pop();
        MainApp.stateStack.push(MainApp.playState);

    }

    @FXML
    private void onSetting(ActionEvent event) {
        System.out.println("SETTING button clicked");
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.out.println("EXIT button clicked");
        System.exit(0);
    }
}
