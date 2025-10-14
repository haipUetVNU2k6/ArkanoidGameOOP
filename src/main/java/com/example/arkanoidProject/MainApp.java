package com.example.arkanoidProject;

import com.example.arkanoidProject.state_controller.state.PauseState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.state_controller.state.StateStack;
import com.example.arkanoidProject.state_controller.state.MenuState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static StackPane root = new StackPane(); // chứa nhiều layer UI
    public static Scene scene = new Scene(root, 600, 800);
    //public static Stage stage;
    public static StateStack stateStack = new StateStack();

    public static MenuState menuState;
    public static PlayState playState;
    public static PauseState pauseState;

    @Override
    public void start(Stage primaryStage) {
        menuState = new MenuState();
        playState = new PlayState();
        pauseState = new PauseState();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.show();

        // Bắt đầu với MenuState
        stateStack.push(menuState);

        // Lắng nghe bàn phím trên Scene và chuyển tiếp cho state trên cùng
        scene.setOnKeyPressed(event -> {
            if (!stateStack.isEmpty()) {
                stateStack.peek().handleKeyPressed(event);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (!stateStack.isEmpty()) {
                stateStack.peek().handleKeyReleased(event);
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                stateStack.update();
                stateStack.render();
            }
        }.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

