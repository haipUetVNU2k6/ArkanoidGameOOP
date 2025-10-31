package com.example.arkanoidProject.state_controller.state;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class State {
    protected Pane root;

    public Pane getUI() { return root; }

}
