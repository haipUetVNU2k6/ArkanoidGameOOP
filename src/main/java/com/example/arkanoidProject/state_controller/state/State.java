package com.example.arkanoidProject.state_controller.state;

public abstract class State {
    public abstract void update();
    public abstract void render(javafx.stage.Stage primaryStage);
}