package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.audio.SoundManager;
import com.example.arkanoidProject.audio.SoundType;
import com.example.arkanoidProject.state_controller.state.MenuState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.state_controller.state.SettingsState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PauseCtrl {
    private int currentLevel;
    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @FXML
    private void onResume(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        System.out.println("resume Click");
        MainApp.stateStack.pop();
    }

    @FXML
    private void onRestart(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.pop();
        MainApp.stateStack.push(new PlayState(currentLevel));
        System.out.println("click on Setting");
    }

    @FXML
    private void onMenu(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.clear();
        MainApp.stateStack.push(new MenuState());
        // làm vòng đời cho state, thay vì static
    }

    @FXML
    private void onSetting(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        MainApp.stateStack.push(new SettingsState());
        System.out.println("click on Setting");
    }
}
