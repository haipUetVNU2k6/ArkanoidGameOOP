package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.util.FireworkCanvas;
import com.example.arkanoidProject.util.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class WinLevelCtrl {
    @FXML
    private StackPane root;

    public void initialize(){
        // Firework background
        FireworkCanvas fireworkCanvas = new FireworkCanvas(Config.getScreenWidth(), Config.getScreenHeight());
        root.getChildren().add(0, fireworkCanvas);
    }

    @FXML
    private void onNextLevel(ActionEvent event) {
        MainApp.stateStack.pop();
        if (!MainApp.stateStack.isEmpty() && MainApp.stateStack.peek() instanceof PlayState playState) {
            playState.loadNextLevel();
        }
    }

    @FXML
    private void onMenu(ActionEvent event) {
        MainApp.stateStack.clear();
        MainApp.stateStack.push(MainApp.menuState);
    }
}
