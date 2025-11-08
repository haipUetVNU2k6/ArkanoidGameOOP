package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.state_controller.controller.LoseLevelCtrl;
import com.example.arkanoidProject.state_controller.controller.PauseCtrl;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class PauseState extends State {
    private int currentLevel;

    public PauseState(int level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/pause.fxml"));
            ui = loader.load();


            PauseCtrl controller = loader.getController();
            controller.setLevel(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.currentLevel = level;
    }

    public int getLevel() {
        return currentLevel;
    }
}
