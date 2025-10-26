package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PauseState extends State {
    private Pane root;

    public PauseState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/pause.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
//        System.out.println("update");
    }

    @Override
    public void render() {}

    @Override
    public Pane getUI() {
        return root;
    }
}
