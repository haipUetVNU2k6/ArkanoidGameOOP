package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.util.ParticleCanvas;
import com.example.arkanoidProject.MainApp;
import com.sun.tools.javac.Main;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuCtrl {
    @FXML
    private StackPane root;

    @FXML
    private Text title;

    @FXML
    private Button btnStart, btnScores, btnSettings, btnExit;

    @FXML
    public void initialize() {
        ParticleCanvas bgCanvas = new ParticleCanvas(MainApp.WIDTH, MainApp.HEIGHT, 1000);
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

        // Hiệu ứng hover cho các nút
        setupButtonHover(btnStart);
        setupButtonHover(btnScores);
        setupButtonHover(btnSettings);
        setupButtonHover(btnExit);

        // Bạn có thể thêm xử lý sự kiện ở đây hoặc trong phương thức riêng
    }

    private void setupButtonHover(Button button) {
        ScaleTransition stEnter = new ScaleTransition(Duration.millis(200), button);
        stEnter.setToX(1.1);
        stEnter.setToY(1.1);

        ScaleTransition stExit = new ScaleTransition(Duration.millis(200), button);
        stExit.setToX(1.0);
        stExit.setToY(1.0);

        button.setOnMouseEntered(e -> stEnter.playFromStart());
        button.setOnMouseExited(e -> stExit.playFromStart());
    }

    @FXML
    private void onPLay(ActionEvent event) {
        System.out.println("PLAY button clicked");
        MainApp.stateStack.pop();
        MainApp.stateStack.push(MainApp.playState);
    }

    @FXML
    private void onHighScores(ActionEvent event) {
        System.out.println("SETTING button clicked");
    }

    @FXML
    private void onSetting(ActionEvent event) {
        System.out.println("SETTING button clicked");
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.out.println("EXIT button clicked");
        System.exit(0);
    }
}

