package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.level.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {
    private Pane root;
    private PlayCtrl controller;

    private GraphicsContext gc;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();

    private final int WIDTH = 600;
    private final int HEIGHT = 800;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private long lastTime = 0;

    private LevelManager levelManager;
    private int level = 1;


    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            root = loader.load();
            controller = loader.getController();

            gc = controller.getGameCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/ball.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle.png").toExternalForm());
            //Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/images/brick.png").toExternalForm());

            ball = new Ball(300, 400, 32, ballSprite, 10, 1, 50, 53, 0.1, WIDTH, HEIGHT);
            paddle = new Paddle(250, 750, 100, 30, paddleSprite, 3, 1, 34, 24, 0.1, WIDTH);

            int brickRows = 5;
            int brickCols = 10;
            int brickWidth = 60;
            int brickHeight = 20;

//            for (int row = 0; row < brickRows; row++) {
//                for (int col = 0; col < brickCols; col++) {
//                    Brick brick = new Brick(col * brickWidth + 10, row * brickHeight + 10, brickWidth, brickHeight,
//                            brickSprite, 31, 18, 9, 1,0.1);
//                    bricks.add(brick);
//                }
//            }

            Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick.png").toExternalForm());
            levelManager = new LevelManager(brickSprite);
            bricks = levelManager.loadCurrentLevel();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        long now = System.nanoTime();
        if (lastTime == 0) {
            lastTime = now;
            return;
        }
        double dt = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        if (leftPressed) {
            paddle.setVelocityX(-400);
        } else if (rightPressed) {
            paddle.setVelocityX(400);
        } else {
            paddle.setVelocityX(0);
        }

        ball.update(dt);
        paddle.update(dt);

        // collision checks...

        // Ball-Paddle collision
        if (ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() + ball.getHeight() > paddle.getY() &&
                ball.getY() < paddle.getY() + paddle.getHeight()) {

            ball.setVelocityY(-Math.abs(ball.getVelocityY()));
        }

        // Ball-Brick collision
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() &&
                    ball.getX() < brick.getX() + brick.getWidth() &&
                    ball.getX() + ball.getWidth() > brick.getX() &&
                    ball.getY() < brick.getY() + brick.getHeight() &&
                    ball.getY() + ball.getHeight() > brick.getY()) {

                brick.destroy();
                ball.setVelocityY(-ball.getVelocityY());
                break;
            }
        }

        // Kiểm tra nếu tất cả brick bị phá
        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }

        if (allDestroyed) {
            // Qua màn mới
            levelManager.nextLevel();
            if (levelManager.hasNextLevel()) {
                bricks = levelManager.loadCurrentLevel();
                ball.resetPosition(300, 400); // bạn cần thêm hàm này trong Ball
                paddle.setX(250);
                level++;
            } else {
                // Hết game → quay lại menu hoặc thắng
                System.out.println("You win all levels!");
                MainApp.stateStack.pop();
                MainApp.stateStack.push(MainApp.menuState);
                return;
            }
        }

    }

    @Override
    public void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        gc.fillText("LEVEL " + level, 20, 30);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = true;
        }
        if (event.getCode() == KeyCode.D) {
            rightPressed = true;
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            lastTime = 0;
            MainApp.stateStack.push(MainApp.pauseState);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        }
        if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    @Override
    public Pane getUI() {
        return root;
    }
}
