package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class SettingsState extends State {

    public SettingsState() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/arkanoidProject/view/fxml/settings.fxml")
            );
            ui = loader.load();

            System.out.println("Settings UI loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading settings.fxml: " + e.getMessage());
        }
    }
}