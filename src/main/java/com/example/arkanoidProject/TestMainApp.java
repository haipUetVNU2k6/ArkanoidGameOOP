//package com.example.arkanoidProject;
//
//import com.example.arkanoidProject.state_controller.state.CurrentState;
//import com.example.arkanoidProject.state_controller.state.MenuState;
//import com.example.arkanoidProject.state_controller.state.PlayState;
//import com.example.arkanoidProject.state_controller.state.PauseState;
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.stage.Stage;
//
//public class MainApp extends Application {
//
//    public static CurrentState currentState = new CurrentState();
//    public static MenuState menuState = new MenuState();
//    public static PlayState playState = new PlayState();
//    public static PauseState pauseState = new PauseState();
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Đẩy menuState lên stack làm state khởi đầu
//        currentState.push(menuState);
//
//        new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                try {
//                    currentState.update();         // Cập nhật state trên cùng
//                    currentState.render(primaryStage); // Render state trên cùng
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }.start();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
