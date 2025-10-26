package com.example.arkanoidProject;

import com.example.arkanoidProject.state_controller.state.*;
import com.example.arkanoidProject.userAccount.User;
import com.example.arkanoidProject.userAccount.UserManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static UserManager userManager = new UserManager();

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;

    public static Stage primaryStage;


    public static StackPane root = new StackPane(); // chứa nhiều layer UI
//    public static Scene scene = new Scene(root, WIDTH, HEIGHT);

    public static Scene scene = new Scene(root);


    public static StateStack stateStack = new StateStack();

    public static MenuState menuState;
    public static PlayState playState;
    public static PauseState pauseState;
    public static ChangeAccountState changeAccountState;

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

        menuState = new MenuState();
        playState = new PlayState();
        pauseState = new PauseState();
        changeAccountState = new ChangeAccountState();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.show();
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();


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

