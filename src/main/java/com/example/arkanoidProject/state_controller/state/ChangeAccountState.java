package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.controller.ChangeAccountCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class ChangeAccountState extends State {

    private Pane root;
    private ChangeAccountCtrl controller;

    public ChangeAccountState() {
        try {
            URL fxmlUrl = getClass().getResource("/com/example/arkanoidProject/view/fxml/test.fxml");
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

    @Override
    public void update() {
        // Không cần logic update mỗi frame
    }

    @Override
    public void render() {
        // Không cần vẽ canvas riêng, FXML sẽ render UI
    }

    @Override
    public void handleKeyPressed(javafx.scene.input.KeyEvent event) {
        // Có thể thêm ESC để quay về menu chính
        switch (event.getCode()) {
            case ESCAPE -> MainApp.stateStack.pop(); // trở về menu trước
        }
    }

    @Override
    public void handleKeyReleased(javafx.scene.input.KeyEvent event) {
    }

    @Override
    public Pane getUI() {
        return root;
    }
}
