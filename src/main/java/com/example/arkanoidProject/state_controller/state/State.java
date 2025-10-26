package com.example.arkanoidProject.state_controller.state;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class State {
    public abstract void update();
    public abstract void render();
    public abstract Pane getUI();

    public void handleKeyPressed(KeyEvent event) {}

    public  void handleKeyReleased(KeyEvent event) {}
}
