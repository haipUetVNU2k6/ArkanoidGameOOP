package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.state_controller.state.ChangeAccountState;
import com.example.arkanoidProject.state_controller.state.ChooseLevelState;
import com.example.arkanoidProject.state_controller.state.HighScoreState;
import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.ParticleCanvas;
import com.example.arkanoidProject.MainApp;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuCtrl {
    @FXML
    private Canvas menuCanvas;

    public Canvas getMenuCanvas() {
        return menuCanvas;
    }

    @FXML
    private StackPane root;

    @FXML
    private Text title;

    @FXML
    private Button btnStart, btnScores, btnSettings, btnExit, btnChangeAccount;

    @FXML
    public void initialize() {
        ParticleCanvas bgCanvas = new ParticleCanvas(Config.getScreenWidth(), Config.getScreenHeight(), 300);
        root.getChildren().add(0, bgCanvas);
        // Hiệu ứng lấp lánh cho tiêu đề
        Timeline titleAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(title.opacityProperty(), 0.7)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(title.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(title.opacityProperty(), 0.7))
        );
        titleAnimation.setCycleCount(Animation.INDEFINITE);
        titleAnimation.setAutoReverse(true);
        titleAnimation.play();

        // Hiệu ứng phóng to và thu nhỏ cho tiêu đề
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), title);
        scaleTransition.setFromX(1.0);  // Kích thước ban đầu
        scaleTransition.setFromY(1.0);  // Kích thước ban đầu
        scaleTransition.setToX(1.2);    // Kích thước phóng to
        scaleTransition.setToY(1.2);    // Kích thước phóng to
        scaleTransition.setCycleCount(Animation.INDEFINITE); // Lặp vô hạn
        scaleTransition.setAutoReverse(true);  // Quay lại kích thước ban đầu sau khi phóng to
        scaleTransition.play();

        Timeline jitter = new Timeline();
        for (int i = 0; i < 1000; i++) {
            double time = i * 0.05;
            double offset = Math.random() * 4 - 2; // dao động ±2 px
            jitter.getKeyFrames().add(new KeyFrame(Duration.seconds(time),
                    new KeyValue(title.translateXProperty(), offset),
                    new KeyValue(title.translateYProperty(), Math.random() * 2 - 1)
            ));
        }
        jitter.setCycleCount(Animation.INDEFINITE);
        jitter.play();




        // Bạn có thể thêm xử lý sự kiện ở đây hoặc trong phương thức riêng
    }


    @FXML
    private void onPLay(ActionEvent event) {
//        System.out.println("PLAY button clicked");
//        MainApp.stateStack.pop();
//        MainApp.stateStack.push(MainApp.playState);

//        MainApp.stateStack.pop();
        MainApp.stateStack.push(new ChooseLevelState());

    }

    @FXML
    private void onHighScores(ActionEvent event) {
        System.out.println("SETTING button clicked");
        MainApp.stateStack.push(new HighScoreState());
    }

    @FXML
    private void onSettings(ActionEvent event) {
        System.out.println("SETTING button clicked");
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.out.println("EXIT button clicked");
        System.exit(0);
    }

    @FXML
    private void onChangeAccount(ActionEvent event) {
        System.out.println("CHANGEACCOUNT button clicked");
        MainApp.stateStack.push(new ChangeAccountState());
    }
}

