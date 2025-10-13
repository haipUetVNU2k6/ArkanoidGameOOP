package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends State {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();

    private Pane root;

    private void initGame() {
        // Paddle
        paddle = new Paddle(250, 750);
        root.getChildren().add(paddle.getNode());

        // Ball
        ball = new Ball(300, 730);
        root.getChildren().add(ball.getNode());

        // Bricks
        int rows = 5;
        int cols = 8;
        double spacing = 5;
        double offsetX = 20;
        double offsetY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick brick = new Brick(
                        offsetX + col * (Brick.WIDTH + spacing),
                        offsetY + row * (Brick.HEIGHT + spacing),
                        javafx.scene.paint.Color.RED
                );
                bricks.add(brick);
                root.getChildren().add(brick.getNode());
            }
        }
    }


    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    PlayState.class.getResource("/com/example/arkanoidProject/fxml/play.fxml")
            );
            root = loader.load();
            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/style.css").toExternalForm());
            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/play.css").toExternalForm());

            initGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            paddle.moveLeft();
        } else if (event.getCode() == KeyCode.RIGHT) {
            paddle.moveRight();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            MainApp.stateStack.push(MainApp.pauseState);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            paddle.stop();
        }
    }



    @Override
    public void update() {
        // Di chuyển paddle và ball
        paddle.updatePosition();
        ball.updatePosition();

        // Va chạm với biên
        if (ball.getX() <= 0 || ball.getX() + Ball.RADIUS * 2 >= root.getWidth()) {
            ball.bounceX();
        }

        if (ball.getY() <= 0) {
            ball.bounceY();
        }

        // Va chạm với paddle
        if (ball.isColliding(paddle)) {
            ball.bounceY();
        }

        // Va chạm với bricks
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (ball.isColliding(brick)) {
                root.getChildren().remove(brick.getNode());
                iterator.remove();
                ball.bounceY();
                break;
            }
        }

        // Game Over nếu ball rơi khỏi màn
        if (ball.getY() >= root.getHeight()) {
            System.out.println("Game Over!");
            // TODO: chuyển sang GameOverState nếu cần
        }
    }

    @Override
    public void render() {
    }

    @Override
    public Pane getUI() {
        return root;
    }
}
