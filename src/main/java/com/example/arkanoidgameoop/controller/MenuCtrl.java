package com.example.arkanoidgameoop.controller;

import com.example.arkanoidgameoop.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuCtrl {
    @FXML
    private Button startBtn;

    @FXML
    private void initialize() {
        // optional init
    }

    @FXML
    private void onStart() {
        try {
            MainApp.showPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}
