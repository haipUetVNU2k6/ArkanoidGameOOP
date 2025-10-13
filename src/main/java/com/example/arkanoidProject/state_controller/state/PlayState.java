package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayState extends State {
    public void update() {}

    private Scene scene;

    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    PlayState.class.getResource("/com/example/arkanoidProject/fxml/play.fxml")
            );
            Parent root = loader.load();

            this.scene = new Scene(root, 600, 800);
            this.scene.getStylesheets().add(
                    PlayState.class.getResource("/com/example/arkanoidProject/css/style.css").toExternalForm()
            );
            this.scene.getStylesheets().add(
                    PlayState.class.getResource("/com/example/arkanoidProject/css/play.css").toExternalForm()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Stage primaryStage) {
        primaryStage.setTitle("Play State");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
