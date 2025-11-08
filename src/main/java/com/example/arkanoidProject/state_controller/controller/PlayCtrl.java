package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.audio.SoundManager;
import com.example.arkanoidProject.audio.SoundType;
import com.example.arkanoidProject.state_controller.state.PauseState;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class PlayCtrl {
    private int currentLevel;
    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @FXML
    private Canvas playCanvas;

    public Canvas getPlayCanvas() {
        return playCanvas;
    }

    @FXML
    private void onPause(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.push(new PauseState(currentLevel));
    }
}
