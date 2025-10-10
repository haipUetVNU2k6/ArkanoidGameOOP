package com.example.arkanoidgameoop.controller;

import com.example.arkanoidgameoop.MainApp;
import com.example.arkanoidgameoop.model.GameManager;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PlayingCtrl {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;

    private GameManager gm;
    private AnimationTimer loop;
    private long lastTime = 0;

    @FXML
    private void initialize() {
        // nothing until shown
    }

    @FXML
    private void onShown() {
        // called manually after scene set if needed
    }

    @FXML
    public void onCreate() {
        // initialize manager and nodes
        gm = new GameManager();
        rebuildNodes();

        // keyboard handlers
        gamePane.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                gm.paddle.moveTo(Math.max(0, gm.paddle.getX() - 40));
                updatePaddleNode();
            } else if (e.getCode() == KeyCode.RIGHT) {
                gm.paddle.moveTo(Math.min(GameManager.WIDTH - gm.paddle.getWidth(), gm.paddle.getX() + 40));
                updatePaddleNode();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                // pause -> show pause scene
                try {
                    MainApp.showPause();
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        // request focus to accept keyboard
        gamePane.requestFocus();

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                gm.update(dt);
                updateBallNode();
                updateBricksNodes();
                scoreLabel.setText("Score: " + gm.score);
                if (gm.gameOver) {
                    stop();
                    try {
                        MainApp.showGameOver();
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        };
        loop.start();
    }

    private void rebuildNodes() {
        gamePane.getChildren().clear();
        // paddle
        Node paddleNode = gm.nodeForPaddle();
        gamePane.getChildren().add(paddleNode);
        // ball
        Node ballNode = gm.nodeForBall();
        gamePane.getChildren().add(ballNode);
        // bricks
        for (Node b : gm.nodesForBricks()) {
            gamePane.getChildren().add(b);
        }
        // ensure focus
        gamePane.requestFocus();
    }

    private void updateBallNode() {
        for (Node n : gamePane.getChildren()) {
            if ("ball".equals(n.getId()) && n instanceof Circle) {
                Circle c = (Circle) n;
                c.setCenterX(gm.ball.getX() + gm.ball.radius);
                c.setCenterY(gm.ball.getY() + gm.ball.radius);
            }
        }
    }

    private void updatePaddleNode() {
        for (Node n : gamePane.getChildren()) {
            if ("paddle".equals(n.getId()) && n instanceof Rectangle) {
                Rectangle r = (Rectangle) n;
                r.setX(gm.paddle.getX());
                r.setY(gm.paddle.getY());
            }
        }
    }

    private void updateBricksNodes() {
        // remove nodes that don't match bricks list (simple approach)
        gamePane.getChildren().removeIf(n -> "brick".equals(n.getId()) && !existsBrickAtNode(n));
        // if bricks removed, nothing else to do (nodes removed above)
    }

    private boolean existsBrickAtNode(Node node) {
        if (!(node instanceof Rectangle)) return true;
        Rectangle r = (Rectangle) node;
        for (com.example.arkanoidgameoop.model.Brick b : gm.bricks) {
            if (Math.abs(b.getX() - r.getX()) < 1 && Math.abs(b.getY() - r.getY()) < 1) {
                return true;
            }
        }
        return false;
    }
}
