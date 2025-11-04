package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class PauseState extends State {
    public PauseState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/pause.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
