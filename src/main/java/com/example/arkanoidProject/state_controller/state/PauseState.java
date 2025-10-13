package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.state_controller.state.State;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PauseState extends State {
    private Pane root;

    public PauseState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/fxml/pause.fxml"));
            root = loader.load();
//            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/style.css").toExternalForm());
//            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/pause.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {}

    @Override
    public void render() {}

    @Override
    public Pane getUI() {
        return root;
    }
}
