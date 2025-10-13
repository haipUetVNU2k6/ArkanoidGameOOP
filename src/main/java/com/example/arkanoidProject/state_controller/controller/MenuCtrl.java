package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.state_controller.state.StateStack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static com.example.arkanoidProject.MainApp.playState;
import static com.example.arkanoidProject.MainApp.stateStack;

public class MenuCtrl {
    @FXML
    private void onPLay(ActionEvent event) {
        System.out.println("PLAY button clicked");
//        currentState.setCurrentState(playState);
        stateStack.pop();
        stateStack.push(playState);

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
