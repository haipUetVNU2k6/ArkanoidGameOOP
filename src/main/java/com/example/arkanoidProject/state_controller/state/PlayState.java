package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import com.example.arkanoidProject.util.Info;
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
    private int levelToLoad;
    private int lives;
    private int scores;



    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            root = loader.load();
            controller = loader.getController();

            gc = controller.getGameCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/cry.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle.png").toExternalForm());
            //Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/images/brick.png").toExternalForm());

           // paddle = new Paddle(Info.PaddleX, Info.PaddleY, Info.PaddleWidth, Info.PaddleHeight, Info.ScreenWidth);
            ball = new Ball(Info.BallX, Info.BallY, Info.BallDiameter, ballSprite, 10, 1, 880, 512, 0.1, Info.ScreenWidth, Info.ScreenHeight);
            paddle = new Paddle(Info.PaddleX, Info.PaddleY, Info.PaddleWidth, Info.PaddleHeight, paddleSprite, 3, 1, 34, 24, 0.1, Info.ScreenWidth);
            scores = 0;
            lives = 3;
            //System.out.println(ball.getY()+","+ paddle.getY());
            int brickRows = 5;
            int brickCols = 10;
            int brickWidth = 60;
            int brickHeight = 20;

// 1. Load sprite
            Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick.png").toExternalForm());
// 2. Khởi tạo LevelManager
            levelManager = new LevelManager(brickSprite);
// 3. Lấy user hiện tại
            int levelToLoad = MainApp.userManager.getCurrentUser().getLastLevel();
// 4. Load màn tương ứng với user
            bricks = levelManager.loadLevel(levelToLoad);

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

        if(ball.isHold()) {
            ball.setX(paddle.getX() + Info.BallX - Info.PaddleX);
        }

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

        // check ball ...
        if(ball.getHeight() + ball.getY() >= Info.ScreenHeight) {
            if(lives > 0) {
                lives--;
                reset();
            }
            else {
                MainApp.stateStack.pop();
                MainApp.stateStack.push(MainApp.menuState);
            }

        }
        // Ball-Paddle collision
//        if (ball.getX() < paddle.getX() + paddle.getWidth() &&
//                ball.getX() + ball.getWidth() > paddle.getX() &&
//                ball.getY() + ball.getHeight() > paddle.getY() &&
//                ball.getY() < paddle.getY() + paddle.getHeight()) {
//
//            ball.setVelocityY(-Math.abs(ball.getVelocityY()));
//        }

        ball.bounceOf(paddle);

        // Ball-Brick collision
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() &&
                    ball.getX() < brick.getX() + brick.getWidth() &&
                    ball.getX() + ball.getWidth() > brick.getX() &&
                    ball.getY() < brick.getY() + brick.getHeight() &&
                    ball.getY() + ball.getHeight() > brick.getY()) {

                brick.takeHit(1);
                brick.destroy();
                ball.setVelocityY(-ball.getVelocityY());
                scores += 10;
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
            // Cập nhật level hoàn thành cho user
            MainApp.userManager.getCurrentUser().setLastLevel(levelToLoad + 1);
            MainApp.userManager.saveUsers();

            levelManager.nextLevel();
            if (levelManager.hasNextLevel()) {
                bricks = levelManager.loadCurrentLevel();
                reset();
                level++;
            } else {
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

        gc.fillText("LEVEL " + level, 20, 10);
        gc.fillText("Scores " + scores, Info.ScreenWidth-50, 10);
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
        if(ball.isHold() && event.getCode()== KeyCode.ENTER) {
            ball.setVelocityX(200);
            ball.setVelocityY(-200);
            ball.setHold(false);
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

    public void reset() {
        ball.reset();
        paddle.reset();
    }

    public void restart() {
        reset();
        for(Brick brick:bricks) {
            brick.setDestroyed(false);
        }
        scores = 0;
        lives = 3;
    }

}
