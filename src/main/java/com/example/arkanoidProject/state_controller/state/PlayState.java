package com.example.arkanoidProject.state_controller.state;

import com.almasb.fxgl.dsl.FXGL;
import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.BallManager;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.object.PowerUp.*;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.HealthText;
import com.example.arkanoidProject.util.StartText;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends State {
    private HealthText healthText;
    private int lives = Config.getLives();
    private double timeSeconds;
    private boolean startTime = false;

    private StartText startText;

    private PlayCtrl controller;

    private GraphicsContext gc;

    private BallManager ballManager;
    private Paddle paddle;
    private PowerUpManager powerUpManager;
    private List<Brick> bricks = new ArrayList<>();

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private long lastTime = 0;

    private LevelManager levelManager;

    private int level;

    private static boolean showHitBox = false;


    public PlayState(int level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            ui = loader.load();
            controller = loader.getController();

            gc = controller.getPlayCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/ball/ballSpriteImage.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle/paddleSpriteImage.png").toExternalForm());

            paddle = new Paddle(Config.getStartPaddleX(), Config.getStartPaddleY(), Config.paddleWidth, Config.paddleHeight,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    Config.paddleHitBoxOffsetX, Config.paddleHitBoxOffsetY, Config.paddleHitBoxW, Config.paddleHitBoxH);

            ballManager = new BallManager(ballSprite);
            Ball mainBall = new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                    ballSprite, 10, 1, 880, 512, 0.1,
                    Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY,
                    Config.ballHitBoxW, Config.ballHitBoxH);

            mainBall.setDy(-384);
            mainBall.setDx(0);
            //System.out.println("dy: " + mainBall.getDy() + " , " +  mainBall.getDx());
            ballManager.addBall(mainBall);

            powerUpManager = new PowerUpManager();

            levelManager = new LevelManager();
//            level = MainApp.userManager.getCurrentUser().getLastLevel();
            this.level = level;
            bricks = levelManager.loadLevel(level);


        } catch (IOException e) {
            e.printStackTrace();
        }

        startText = new StartText(Config.getScreenWidth() / 2, Config.getScreenHeight() * 0.8, level);
        healthText = new HealthText(2, 10);

    }

    public void resetBallPaddle() {
        Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/ball/ballSpriteImage.png").toExternalForm());
        Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle/paddleSpriteImage.png").toExternalForm());

        paddle = new Paddle(Config.getStartPaddleX(), Config.getStartPaddleY(), Config.paddleWidth, Config.paddleHeight,
                paddleSprite, 8, 1, 800, 640, 0.1,
                Config.paddleHitBoxOffsetX, Config.paddleHitBoxOffsetY, Config.paddleHitBoxW, Config.paddleHitBoxH);

        ballManager.reset();
        ballManager.addBall(new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                ballSprite, 10, 1, 880, 512, 0.1,
                Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY, Config.ballHitBoxW, Config.ballHitBoxH));

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

        // ======== INPUT ========
        if (leftPressed) {
            paddle.setDx(-400);
        } else if (rightPressed) {
            paddle.setDx(400);
        } else {
            paddle.setDx(0);
        }

        startText.update(dt);
        if (startTime) timeSeconds += dt;
        int timeSecondsInt = (int) timeSeconds;
        healthText.update(lives, timeSecondsInt);
        startText.update(dt);

        // ======== UPDATE OBJECTS ========
        double oldPaddleX = paddle.getX();
        paddle.update(dt);

        for (Ball ball : ballManager.getBalls()) {
            if (ball.isHeld()) {

                ball.setX(ball.getX() + paddle.getX() - oldPaddleX);
                ball.setY(paddle.getHitBox().getMinY() - ball.getHeight());
            }
            else {

                ball.update(dt);
            }
        }

//        System.out.println(
//                Math.round(Config.getScreenWidth()) + " " +
//                        Math.round(Config.getScreenHeight()) + " " +
//                        Math.round(ball.getHitBox().getMinX()) + " " +
//                        Math.round(ball.getHitBox().getMinY())
//        );

        // ======== BALL - WALL COLLISION ========

        Iterator<Ball> iterator = ballManager.getBalls().iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();

            if (ball.getHitBox().getMinX() <= 0) {
                ball.setX(ball.getX() - ball.getHitBox().getMinX());
                ball.setDx(-ball.getDx());
            }

            if (ball.getHitBox().getMaxX() >= Config.getScreenWidth()) {
                ball.setX(ball.getX() - (ball.getHitBox().getMaxX() - Config.getScreenWidth()));
                ball.setDx(-ball.getDx());
            }

            if (ball.getHitBox().getMinY() <= 0) {
                ball.setY(ball.getY() - ball.getHitBox().getMinY());
                ball.setDy(-ball.getDy());
            }

            if (ball.getHitBox().getMaxY() >= Config.getScreenHeight()) {
                iterator.remove();
                if (ballManager.getBalls().isEmpty()) {
                    lives--;
                    resetBallPaddle();
                    startTime = false;
                    break;
                }
            }
        }


        // ======== BALL - PADDLE COLLISION ========
        for (Ball ball : ballManager.getBalls()) {
            if (ball.isHeld()) {
                continue;
            }

            if (ball.getHitBox().intersects(paddle.getHitBox())) {
                if (paddle.isHoldingBall()) {
                    ball.setHeld(true);
                    double paddleCenterX = paddle.getX()
                            + Config.paddleHitBoxW / 2 - Config.ballHitBoxW / 2
                            -   Config.ballHitBoxOffsetX;
                    ball.setY(paddle.getHitBox().getMinY() - ball.getHeight());
                    ball.setX(paddleCenterX);
                    ball.setDx(0);

                } else {
                    double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
                    double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight() / 2;
                    double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
                    double paddleCenterY = paddle.getHitBox().getMinY() + paddle.getHitBox().getHeight() / 2;

                    ball.setDx((ballCenterX - paddleCenterX) * Config.ballDxMultiple);
                    ball.setDy((ballCenterY - paddleCenterY) * Config.ballDyMultiple);
                }
            }

        }

        // ======== BALL - BRICK COLLISION ========

        // lý do dùng overlap thay cho intersect là gì, intersect chỉ trả về true/false
        // ko biết được hướng đến của ball từ đâu để tính dx, dy phản xạ
        for (Ball ball : ballManager.getBalls()) {
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
                if (brick.isDestroyed()) {
                    powerUpManager.onBrickDestroyed(brick);
                }
                break; // tránh phá nhiều brick 1 frame
            }
        }
        //     PowerUp-- Collision
        Iterator<PowerUp> ite = powerUpManager.getActivePowerUps().iterator();
        while (ite.hasNext()) {
            PowerUp element = ite.next();
            if (paddle.getHitBox().intersects(element.getHitBox())) {
                if (element instanceof MultiBallPowerUp) {
                    if (!ballManager.getBalls().isEmpty()) ballManager.spawnExtraBalls(ballManager.getBalls().get(0));
                }
                else if(element instanceof ExtraLivesPowerUp) {
                    lives++;
                }
                else if(element instanceof ExtraTimePowerUp) {
                    timeSeconds = timeSeconds / 2;
                }
                else {
                    powerUpManager.applyPowerUp(element, paddle, ballManager);
                }
                powerUpManager.getActivePowerUps().remove(element);
            }
            element.update(dt);
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
            if (level == MainApp.userManager.getCurrentUser().getLastLevel()) {
                MainApp.userManager.getCurrentUser().setLastLevel(level + 1);
            }
            MainApp.userManager.saveUsers();

            MainApp.stateStack.push(new WinLevelState(level));
            // truyền level vào WinLevelState, sau đó WLS sẽ truyền level WLCtrl để
            // WLCtrl gọi tạo PlayState mới với level+1;

            if (timeSecondsInt < MainApp.userManager.getCurrentUser().getLevelResult(level)) {
                MainApp.userManager.getCurrentUser().setLevelResult(level, timeSecondsInt);
            }
        }

        // ======== CHECK LOSE ========
        if (lives == 0) {
            MainApp.stateStack.push(new LoseLevelState(level));
        }

    }

    @Override
    public void render() {
        gc.clearRect(0, 0, Config.getScreenWidth(), Config.getScreenHeight());

        startText.render(gc);
        healthText.render(gc);
        ballManager.renderAll(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        Iterator<PowerUp> iterator = powerUpManager.getActivePowerUps().iterator();
        while (iterator.hasNext()) {
            PowerUp element = iterator.next();
            element.render(gc);
        }

        if (showHitBox) {
            for (Ball ball : ballManager.getBalls()) {
                ball.showHitBox(gc);
            }
            paddle.showHitBox(gc);
        }
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
            MainApp.stateStack.push(new PauseState());
        }
        if (event.getCode() == KeyCode.H) {
            showHitBox = !showHitBox;
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

        //phím Space bị JavaFX "ăn" mất
        if (event.getCode() == KeyCode.SPACE && !ballManager.getBalls().isEmpty()) {
            System.out.println("Space pressed");

          //  boolean anyBallWasHeld = false;

            for (Ball ball : ballManager.getBalls()) {
                if (paddle.isHoldingBall()) {
                    if (ball.isHeld()) {
                        ball.stopHolding();
                        ball.setDx(0);
                        ball.setDy(-Math.abs(Config.startBallDy) * 4);
                    }

                } else {
                    ball.stopHolding();
                    startText.hide();
                    startTime = true;
                }
            }
        }

    }

}
