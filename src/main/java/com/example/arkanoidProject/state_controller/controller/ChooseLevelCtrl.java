package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.state.ChooseLevelState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChooseLevelCtrl {

    @FXML private Button level1Btn;
    @FXML private Button level2Btn;
    @FXML private Button level3Btn;
    @FXML private Button level4Btn;
    @FXML private Button level5Btn;
    @FXML private Button level6Btn;
    @FXML private Button backBtn;

    private int unlockedLevel;

    public void init(ChooseLevelState state) {
        unlockedLevel = MainApp.userManager.getCurrentUser().getLastLevel();
        setupButtons();
        setupBackButton();
    }

    private void setupButtons() {
        setupButton(level1Btn, 1);
        setupButton(level2Btn, 2);
        setupButton(level3Btn, 3);
        setupButton(level4Btn, 4);
        setupButton(level5Btn, 5);
        setupButton(level6Btn, 6);
    }

    private void setupButton(Button btn, int level) {
        if (level <= unlockedLevel) {
            btn.getStyleClass().add("level-unlocked");
            btn.setOnAction(e -> onSelectLevel(level));
        } else {
            btn.getStyleClass().add("level-locked");
            btn.setDisable(true);
        }
    }

    private void setupBackButton() {
        backBtn.setOnAction(e -> MainApp.stateStack.pop());
    }

    private void onSelectLevel(int level) {
        System.out.println("Selected Level: " + level);
        MainApp.stateStack.push(MainApp.playState); // Chuyển sang PlayState
    }
}
