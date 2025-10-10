package com.example.arkanoidgameoop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidgameoop/main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, Game.Manage.GameManager.WIDTH, Game.Manage.GameManager.HEIGHT);
        stage.setTitle("Arkanoid Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
