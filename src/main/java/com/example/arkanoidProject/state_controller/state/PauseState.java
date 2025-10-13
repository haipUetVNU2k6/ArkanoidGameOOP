package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.state_controller.state.State;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PauseState extends State {
    private VBox overlay;

    public PauseState() {
        overlay = new VBox();
        overlay.setPrefSize(600, 800);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        Label paused = new Label("Game Paused");
        paused.setStyle("-fx-text-fill: white; -fx-font-size: 32px;");
        overlay.getChildren().add(paused);
    }

    @Override
    public void update() {}

    @Override
    public void render() {}

    @Override
    public Pane getUI() {
        return overlay;
    }
}
