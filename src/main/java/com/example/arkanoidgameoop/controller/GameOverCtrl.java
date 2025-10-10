package com.example.arkanoidgameoop.controller;

import com.example.arkanoidgameoop.MainApp;
import javafx.fxml.FXML;

public class GameOverCtrl {
    @FXML
    private void onRestart() {
        try {
            MainApp.showPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenu() {
        try {
            MainApp.showMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
