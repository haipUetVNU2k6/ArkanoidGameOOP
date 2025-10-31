package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import com.example.arkanoidProject.util.Info;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {
    private PlayCtrl controller;

    private GraphicsContext gc;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();


    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private long lastTime = 0;

    private LevelManager levelManager;
    private int level = 1;

    private int levelToLoad;

    private static boolean showHitBox = false;


    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            root = loader.load();
            controller = loader.getController();

            gc = controller.getGameCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/ball/ballSpriteImage.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle/paddleSpriteImage.png").toExternalForm());

            paddle = new Paddle(Info.startPaddleX, Info.startPaddleY, Info.paddleWidth, Info.paddleHeight,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    Info.paddleHitBoxOffsetX, Info.paddleHitBoxOffsetY, Info.paddleHitBoxW, Info.paddleHitBoxH);

            ball = new Ball(Info.startBallX, Info.startBallY, Info.ballWidth, Info.ballHeight,
                    ballSprite, 10, 1, 880, 512, 0.1,
                    Info.ballHitBoxOffsetX, Info.ballHitBoxOffsetY, Info.ballHitBoxW, Info.ballHitBoxH);

            levelManager = new LevelManager();
            int levelToLoad = MainApp.userManager.getCurrentUser().getLastLevel();
            bricks = levelManager.loadLevel(levelToLoad);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        long now = System.nanoTime();
        if (lastTime == 0) {
            lastTime = now;
            return;
        }
        double dt = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        // ======== INPUT ========
        if (leftPressed) {
            paddle.setDx(-400);
        } else if (rightPressed) {
            paddle.setDx(400);
        } else {
            paddle.setDx(0);
        }

        // ======== UPDATE OBJECTS ========
        double oldPaddleX = paddle.getX();
        paddle.update(dt);

        if (ball.isHeld()) {
            ball.setX(ball.getX() + (paddle.getX() - oldPaddleX));
        } else ball.update(dt);

        System.out.println(
                Math.round(Info.ScreenWidth) + " " +
                        Math.round(Info.ScreenHeight) + " " +
                        Math.round(ball.getHitBox().getMinX()) + " " +
                        Math.round(ball.getHitBox().getMinY())
        );

        // ======== BALL - WALL COLLISION ========
        if (ball.getHitBox().getMinX() <= 0) {
            ball.setX(ball.getX() - ball.getHitBox().getMinX());
            ball.setDx(- ball.getDx());
        }
        if (ball.getHitBox().getMaxX() >= Info.ScreenWidth) {
            ball.setX(ball.getX() - (ball.getHitBox().getMaxX() - Info.ScreenWidth));
            ball.setDx(-ball.getDx());
        }
        if (ball.getHitBox().getMinY() <= 0) {
            ball.setY(ball.getY() - ball.getHitBox().getMinY());
            ball.setDy(-ball.getDy());
        }
        if (ball.getHitBox().getMaxY() >= Info.ScreenHeight) {
            ball.setY(ball.getY() - (ball.getHitBox().getMaxY() - Info.ScreenHeight));
            ball.setDy(-ball.getDy());
        }

        // ======== BALL - PADDLE COLLISION ========
        if (ball.getHitBox().intersects(paddle.getHitBox())) {
            double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
            double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight() / 2;
            double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
            double paddleCenterY = paddle.getHitBox().getMinY() + paddle.getHitBox().getHeight() / 2;

            // Hệ số multiple dùng để tăng tốc ball.
            ball.setDx((ballCenterX - paddleCenterX) * Info.ballDxMultiple);
            ball.setDy((ballCenterY - paddleCenterY) * Info.ballDyMultiple);
        }

        // ======== BALL - BRICK COLLISION ========
        Rectangle2D ballBox = ball.getHitBox();
        for (Brick brick : bricks) {
            if (brick.isDestroyed()) continue;

            Rectangle2D brickBox = new Rectangle2D(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());

            if (!ballBox.intersects(brickBox)) continue;

            double ballLeft = ballBox.getMinX();
            double ballRight = ballBox.getMaxX();
            double ballTop = ballBox.getMinY();
            double ballBottom = ballBox.getMaxY();

            double brickLeft = brick.getX();
            double brickRight = brick.getX() + brick.getWidth();
            double brickTop = brick.getY();
            double brickBottom = brick.getY() + brick.getHeight();

            // Tính độ chồng lấn
            double overlapLeft = ballRight - brickLeft;
            double overlapRight = brickRight - ballLeft;
            double overlapTop = ballBottom - brickTop;
            double overlapBottom = brickBottom - ballTop;

            boolean ballFromLeft = overlapLeft < overlapRight;
            boolean ballFromTop = overlapTop < overlapBottom;

            double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
            double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

            // Va vào trục nào ít hơn → cạnh đó
            if (minOverlapX < minOverlapY) {
                // X COLLISION (trái/phải)
                ball.setDx(-ball.getDx());

                if (ballFromLeft)
                    ball.setX(brickLeft - ball.getWidth());
                else
                    ball.setX(brickRight);
            } else {
                // Y COLLISION (trên/dưới)
                ball.setDy(-ball.getDy());

                if (ballFromTop)
                    ball.setY(brickTop - ball.getHeight());
                else
                    ball.setY(brickBottom);
            }

            brick.takeDamage();
            break; // tránh phá nhiều brick 1 frame
        }


        // ======== CHECK WIN ========
        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }

        // Win level
        if (allDestroyed) {
            MainApp.stateStack.push(MainApp.winLevelState);
            MainApp.userManager.getCurrentUser().setLastLevel(levelToLoad + 1);
            MainApp.userManager.saveUsers();
            levelManager.nextLevel();

            return;
        }
    }

    public void loadNextLevel() {
        if (levelManager.hasNextLevel()) {
            bricks = levelManager.loadCurrentLevel();
            ball.resetPosition(300, 400);
            paddle.setX(250);
            level++;
        } else {
            System.out.println("🎉 You win all levels!");
            MainApp.stateStack.pop();
            MainApp.stateStack.push(MainApp.menuState);
        }
    }

    public void render() {
        gc.clearRect(0, 0, Info.ScreenWidth, Info.ScreenHeight);

        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        if (showHitBox) {
            ball.showHitBox(gc);
            paddle.showHitBox(gc);
        }

        gc.fillText("LEVEL " + level, 20, 30);
    }

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
        if (event.getCode() == KeyCode.H) {
            showHitBox = !showHitBox;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        }
        if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
        if (event.getCode() == KeyCode.SPACE) {
            ball.stopHolding();
        }
    }
}
