package com.example.arkanoidProject;

import com.example.arkanoidProject.state_controller.state.*;
import com.example.arkanoidProject.userAccount.User;
import com.example.arkanoidProject.userAccount.UserManager;
import com.example.arkanoidProject.util.Config;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    public static UserManager userManager = new UserManager();

    public static Stage primaryStage;


    public static StackPane root = new StackPane(); // chứa nhiều layer UI

    public static Scene scene = new Scene(root);


    public static StateStack stateStack = new StateStack();

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Nếu chưa có user nào, tạo user mặc định
        if (userManager.getUsers().isEmpty()) {
            User defaultUser = new User("Player1");
            userManager.addUser(defaultUser);
            userManager.setCurrentUser(defaultUser);
        } else if (userManager.getCurrentUser() == null) {
            // Nếu có user trong danh sách nhưng chưa set currentUser
            userManager.setCurrentUser(userManager.getUsers().get(0));
        }

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.show();
        primaryStage.setWidth(Config.getScreenWidth());
        primaryStage.setHeight(Config.getScreenHeight());
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();


        // Bắt đầu với MenuState
        stateStack.push(new MenuState());

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
                if (!stateStack.isEmpty()) {
                    stateStack.peek().update();
                    stateStack.peek().render();
                }
            }
        }.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

