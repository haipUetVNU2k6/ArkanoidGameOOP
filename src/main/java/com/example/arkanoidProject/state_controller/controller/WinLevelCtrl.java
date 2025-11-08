package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.audio.SoundManager;
import com.example.arkanoidProject.audio.SoundType;
import com.example.arkanoidProject.state_controller.state.MenuState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.util.FireworkCanvas;
import com.example.arkanoidProject.util.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class WinLevelCtrl {
    private int currentLevel;
    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @FXML
    private StackPane root;

    public void initialize(){
        // Firework background
        FireworkCanvas fireworkCanvas = new FireworkCanvas(Config.getScreenWidth(), Config.getScreenHeight());
        root.getChildren().add(0, fireworkCanvas);
    }

    @FXML
    private void onNextLevel(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.pop();
        MainApp.stateStack.push(new PlayState(currentLevel % 6 + 1));
    }

    @FXML
    private void onRestart(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.pop();
        MainApp.stateStack.push(new PlayState(currentLevel));
    }

    @FXML
    private void onMenu(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.clear();
        MainApp.stateStack.push(new MenuState());
    }
}
