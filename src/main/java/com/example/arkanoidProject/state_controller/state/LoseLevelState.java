package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.state_controller.controller.LoseLevelCtrl;
import com.example.arkanoidProject.state_controller.controller.WinLevelCtrl;
import com.example.arkanoidProject.util.FireworkCanvas;
import com.example.arkanoidProject.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LoseLevelState extends State{
    private int currentLevel;

    public LoseLevelState(int level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/loseLevel.fxml"));
            ui = loader.load();

            // Lấy controller để truyền dữ liệu
            LoseLevelCtrl controller = loader.getController();
            controller.setLevel(level);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentLevel = level;
    }

    public int getLevel() {
        return currentLevel;
    }
}
