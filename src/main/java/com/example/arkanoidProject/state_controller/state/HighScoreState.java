package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.controller.HighScoreCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class HighScoreState extends State {
    private Parent root;
    private HighScoreCtrl controller;

    public HighScoreState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/highScore.fxml"));
            root = loader.load();
            controller = loader.getController();
            controller.setState(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnter() {
        MainApp.root.getChildren().add(root);
        controller.loadHighScores();
    }

    @Override
    public void onExit() {
        MainApp.root.getChildren().remove(root);
    }

    @Override
    public void update() {
        // Update logic nếu cần (animations, etc.)
    }

    @Override
    public void render() {
        // Render logic nếu cần
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        controller.handleKeyPressed(event);
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        // Không cần xử lý
    }

    public void backToMenu() {
        MainApp.stateStack.pop();
        MainApp.stateStack.push(new MenuState());
    }
}