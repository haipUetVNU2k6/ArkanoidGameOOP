package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.state.MenuState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.util.FireworkCanvas;
import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.RainCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class LoseLevelCtrl {
    private int currentLevel;
    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @FXML
    private StackPane root;

    public void initialize(){
        RainCanvas rainCanvas = new RainCanvas(Config.getScreenWidth(), Config.getScreenHeight());
        root.getChildren().add(0, rainCanvas);
    }

    @FXML
    private void onRestart(ActionEvent event) {
        MainApp.stateStack.pop();
        MainApp.stateStack.push(new PlayState(currentLevel));
    }

    @FXML
    private void onMenu(ActionEvent event) {
        MainApp.stateStack.clear();
        MainApp.stateStack.push(new MenuState());
    }
}
