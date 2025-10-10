package com.example.arkanoidgameoop;

import Game.Manage.GameManager;
import Game.Object.Ball;
import Game.Object.Brick;
import Game.Object.Paddle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class MainController {

    @FXML
    private Canvas gameCanvas;

    private GameManager gameManager;
    private GraphicsContext gc;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @FXML
    public void initialize() {
        gameManager = new GameManager();
        gc = gameCanvas.getGraphicsContext2D();

        gameManager.startGame();

        setupInputHandling();

        startGameLoop();
    }

    private void setupInputHandling() {
        gameCanvas.setFocusTraversable(true);

        gameCanvas.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed = true;
                case RIGHT -> rightPressed = true;
                default -> {}
            }
        });

        gameCanvas.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed = false;
                case RIGHT -> rightPressed = false;
                default -> {}
            }
        });
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        timer.start();
    }

    private void update() {
        Paddle paddle = gameManager.getPaddle();
        if (leftPressed) paddle.moveLeft();
        if (rightPressed) paddle.moveRight();

        gameManager.updateGame();
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        Paddle paddle = gameManager.getPaddle();
        Ball ball = gameManager.getBall();

        paddle.render(gc);
        ball.render(gc);

        for (Brick brick : gameManager.getBricks()) {
            brick.render(gc);
        }

        gc.fillText("Score: " + gameManager.getScores(), 10, 20);
        gc.fillText("Lives: " + gameManager.getLives(), 10, 40);
    }
}
