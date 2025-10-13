package com.example.arkanoidProject;

import com.example.arkanoidProject.state_controller.state.CurrentState;
import com.example.arkanoidProject.state_controller.state.MenuState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import com.example.arkanoidProject.state_controller.state.SettingState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static CurrentState currentState = new CurrentState();
    public static MenuState menuState = new MenuState();
    public static PlayState playState = new PlayState();
//    public static PauseState pauseState = new PauseState();
//    public static GameOverState gameOverState = new GameOverState();
//    public static SettingState settingState = new SettingState();


    @Override
    public void start(Stage primaryStage) throws Exception {
        currentState.setCurrentState(menuState);
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    currentState.render(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
