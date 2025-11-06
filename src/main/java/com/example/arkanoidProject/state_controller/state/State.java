package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class State {
    protected Pane ui;

    public Pane getUI() { return ui; }

    // Gọi khi state được push lên stack
    public void onEnter() {
        MainApp.root.getChildren().add(ui);
    }

    // Gọi khi state bị pop khỏi stack
    public void onExit() {
        MainApp.root.getChildren().remove(ui);
    }

}


// onEnter, onExit giúp tránh:
//1. animation, timeline, listener vẫn chạy
//2. state vĩnh viễn sống kể cả khi ko dùng -> memory leak