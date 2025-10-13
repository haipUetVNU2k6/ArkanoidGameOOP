package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuState extends State{
    public void update() {}

    private Scene scene;

    public MenuState() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MenuState.class.getResource("/com/example/arkanoidProject/fxml/menu.fxml")
            );
            Parent root = loader.load();

            this.scene = new Scene(root, 600, 800);
            this.scene.getStylesheets().add(
                    MenuState.class.getResource("/com/example/arkanoidProject/css/style.css").toExternalForm()
            );
            this.scene.getStylesheets().add(
                    MenuState.class.getResource("/com/example/arkanoidProject/css/menu.css").toExternalForm()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Stage primaryStage) {
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
