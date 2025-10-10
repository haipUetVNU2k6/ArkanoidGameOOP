package com.example.arkanoidgameoop;

import com.example.arkanoidgameoop.model.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showMenu();
    }

    public static void showMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/example/arkanoidgameoop/fxml/menu.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, GameManager.WIDTH, GameManager.HEIGHT);
        scene.getStylesheets().add(
                MainApp.class.getResource("/com/example/arkanoidgameoop/css/style.css").toExternalForm()
        );
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static void showPlaying() throws Exception {
//        FXMLLoader loader = new FXMLLoader(
//                MainApp.class.getResource("/com/example/arkanoidgameoop/fxml/playing.fxml")
//        );
//        Parent root = loader.load();
//        Scene scene = new Scene(root, GameManager.WIDTH, GameManager.HEIGHT);
//        scene.getStylesheets().add(
//                MainApp.class.getResource("/com/example/arkanoidgameoop/css/style.css").toExternalForm()
//        );
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void showPlaying() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/example/arkanoidgameoop/fxml/playing.fxml")
        );
        Parent root = loader.load();

        // Lấy controller
        com.example.arkanoidgameoop.controller.PlayingCtrl ctrl = loader.getController();

        Scene scene = new Scene(root, GameManager.WIDTH, GameManager.HEIGHT);
        scene.getStylesheets().add(
                MainApp.class.getResource("/com/example/arkanoidgameoop/css/style.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.show();

        // Gọi onCreate() sau khi stage đã show
        ctrl.onCreate();
    }


    public static void showPause() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/example/arkanoidgameoop/fxml/pause.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, GameManager.WIDTH, GameManager.HEIGHT);
        scene.getStylesheets().add(
                MainApp.class.getResource("/com/example/arkanoidgameoop/css/style.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showGameOver() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/example/arkanoidgameoop/fxml/gameOver.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, GameManager.WIDTH, GameManager.HEIGHT);
        scene.getStylesheets().add(
                MainApp.class.getResource("/com/example/arkanoidgameoop/css/style.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
