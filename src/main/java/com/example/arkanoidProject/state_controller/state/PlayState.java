package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.object.PowerUp.PowerUp;
import com.example.arkanoidProject.object.PowerUp.PowerUpManager;
import com.example.arkanoidProject.sound.SoundEffect;
import com.example.arkanoidProject.sound.SoundManager;
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
    private int lives = 3;
    private double timeSeconds;
    private boolean startTime = false;
    private StartText startText;
    private PlayCtrl controller;
    private GraphicsContext gc;

    private Ball ball;
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


            ball = new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                    ballSprite, 10, 1, 880, 512, 0.1,
                    Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY, Config.ballHitBoxW, Config.ballHitBoxH);


            powerUpManager = new PowerUpManager();
            levelManager = new LevelManager();
            this.level = level;
            bricks = levelManager.loadLevel(level);


            SoundManager.loopSound(SoundEffect.GAME_LOOP);
        } catch (IOException e) {
            System.err.println("Lỗi tải FXML hoặc tài nguyên trong PlayState: " + e.getMessage());
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

        ball = new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                ballSprite, 10, 1, 880, 512, 0.1,
                Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY, Config.ballHitBoxW, Config.ballHitBoxH);
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


        if (leftPressed) paddle.setDx(-400);
        else if (rightPressed) paddle.setDx(400);
        else paddle.setDx(0);

        startText.update(dt);
        if (startTime) timeSeconds += dt;
        healthText.update(lives, (int) timeSeconds);
        double oldPaddleX = paddle.getX();
        paddle.update(dt);
        if (ball.isHeld()) {

            ball.setX(ball.getX() + (paddle.getX() - oldPaddleX));

        } else {

            ball.update(dt);
        }

        if (startTime) {

            // === BALL - WALL ===
            if (ball.getHitBox().getMinX() <= 0 || ball.getHitBox().getMaxX() >= Config.getScreenWidth()) {
                ball.setDx(-ball.getDx());
            }
            if (ball.getHitBox().getMinY() <= 0) {
                ball.setDy(-ball.getDy());
            }

            if (ball.getHitBox().getMaxY() >= Config.getScreenHeight()) {
                lives--;
                SoundManager.playSound(SoundEffect.BALL_LOST);
                resetBallPaddle();
                startTime = false;
                if (lives <= 0) {
                    SoundManager.stopSound(SoundEffect.GAME_LOOP);
                    SoundManager.playSound(SoundEffect.GAME_LOSE);
                    MainApp.stateStack.push(new LossLevelState());
                    return;
                }
            }

            // === BALL - PADDLE  ===
            if (ball.getHitBox().intersects(paddle.getHitBox())) {
                SoundManager.playSound(SoundEffect.BALL_PADDLE);

                double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
                double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight() / 2;
                double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
                double paddleCenterY = paddle.getHitBox().getMinY() + paddle.getHitBox().getHeight() / 2;
                ball.setDx((ballCenterX - paddleCenterX) * Config.ballDxMultiple);
                ball.setDy((ballCenterY - paddleCenterY) * Config.ballDyMultiple);
            }

            // === BALL - BRICK===
            Rectangle2D ballBox = ball.getHitBox();
            for (Brick brick : bricks) {
                if (brick.isDestroyed()) continue;
                Rectangle2D brickBox = new Rectangle2D(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                if (!ballBox.intersects(brickBox)) continue;

                SoundManager.playSound(SoundEffect.BALL_BRICK);


                double ballLeft = ballBox.getMinX();
                double ballRight = ballBox.getMaxX();
                double ballTop = ballBox.getMinY();
                double ballBottom = ballBox.getMaxY();
                double brickLeft = brick.getX();
                double brickRight = brick.getX() + brick.getWidth();
                double brickTop = brick.getY();
                double brickBottom = brick.getY() + brick.getHeight();

                double overlapLeft = ballRight - brickLeft;
                double overlapRight = brickRight - ballLeft;
                double overlapTop = ballBottom - brickTop;
                double overlapBottom = brickBottom - ballTop;

                boolean ballFromLeft = overlapLeft < overlapRight;
                boolean ballFromTop = overlapTop < overlapBottom;

                double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
                double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

                if (minOverlapX < minOverlapY) {
                    ball.setDx(-ball.getDx());
                    ball.setX(ballFromLeft ? brickLeft - ball.getWidth() : brickRight);
                } else {
                    ball.setDy(-ball.getDy());
                    ball.setY(ballFromTop ? brickTop - ball.getHeight() : brickBottom);
                }

                brick.takeDamage();
                if (brick.isDestroyed()) {
                    SoundManager.playSound(SoundEffect.BRICK_DESTROY);
                    powerUpManager.onBrickDestroyed(brick);
                }
                break;
            }

            // === POWERUPS ===
            Iterator<PowerUp> ite = powerUpManager.getActivePowerUps().iterator();
            while (ite.hasNext()) {
                PowerUp element = ite.next();
                element.update(dt);
                if (paddle.getHitBox().intersects(element.getHitBox())) {
                    powerUpManager.applyPowerUp(element, paddle);
                    ite.remove();
                }
            }

            // === WIN  ===
            boolean allDestroyed = true;
            for (Brick brick : bricks)
                if (!brick.isDestroyed()) { allDestroyed = false; break; }

            if (allDestroyed) {
                SoundManager.stopSound(SoundEffect.GAME_LOOP);
                SoundManager.playSound(SoundEffect.GAME_WIN);
                MainApp.userManager.getCurrentUser().setLastLevel(level + 1);
                MainApp.userManager.saveUsers();
                MainApp.stateStack.push(new WinLevelState(level));
            }
        }
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, Config.getScreenWidth(), Config.getScreenHeight());
        startText.render(gc);
        healthText.render(gc);
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) brick.render(gc);
        for (PowerUp p : powerUpManager.getActivePowerUps()) p.render(gc);
        if (showHitBox) {
            ball.showHitBox(gc);
            paddle.showHitBox(gc);
        }
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) leftPressed = true;
        if (event.getCode() == KeyCode.D) rightPressed = true;
        if (event.getCode() == KeyCode.ESCAPE) {
            lastTime = 0;
            MainApp.stateStack.push(new PauseState());
        }
        if (event.getCode() == KeyCode.H) showHitBox = !showHitBox;
        if (event.getCode() == KeyCode.SPACE) {
            SoundManager.playSound(SoundEffect.BUTTON_CLICK);
            ball.stopHolding();
            startText.hide();
            startTime = true;
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) leftPressed = false;
        if (event.getCode() == KeyCode.D) rightPressed = false;
    }
}
