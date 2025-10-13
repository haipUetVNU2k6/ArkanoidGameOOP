package com.example.arkanoidProject.state_controller.state;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class State {
    public abstract void update();
    public abstract void render();
    public abstract Pane getUI(); // mỗi State phải có giao diện riêng

    public void handleKeyPressed(KeyEvent event) {
        // Mặc định không làm gì, override trong state con nếu cần
    }

    // Xử lý phím thả
    public void handleKeyReleased(KeyEvent event) {
        // Mặc định không làm gì, override trong state con nếu cần
    }
}
