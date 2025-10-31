package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.controller.ChangeAccountCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class ChangeAccountState extends State {
    private ChangeAccountCtrl controller;

    public ChangeAccountState() {
        try {
            URL fxmlUrl = getClass().getResource("/com/example/arkanoidProject/view/fxml/changeAccount.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("Cannot find changeAccount.fxml!");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            root = loader.load();
            controller = loader.getController();

            if (root == null || controller == null) {
                throw new RuntimeException("Failed to load ChangeAccountState FXML or controller!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
