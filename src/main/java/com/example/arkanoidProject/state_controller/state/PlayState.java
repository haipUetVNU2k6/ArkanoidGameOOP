package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.audio.SoundManager;
import com.example.arkanoidProject.audio.SoundType;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.object.powerup.PowerUp;
import com.example.arkanoidProject.object.powerup.PowerUpContext;
import com.example.arkanoidProject.object.powerup.PowerUpFactory;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.ExplosionCanvas;
import com.example.arkanoidProject.util.HealthText;
import com.example.arkanoidProject.util.StartText;
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
    private ExplosionCanvas explosionCanvasManager;

    private HealthText healthText;
    private int lives = Config.getLives();
    private double timeSeconds;
    private boolean startTime = false;

    private StartText startText;
    private PlayCtrl controller;
    private GraphicsContext gc;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();

    // PowerUp system
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<Ball> balls = new ArrayList<>();
    private List<Paddle> sidePaddles = new ArrayList<>();
    private double[] triplePaddleTimer = {0};

    private boolean leftPressed = false;
    private boolean rightPressed = false;

//    private long lastTime = 0;

    private LevelManager levelManager;
    private int level;

    private static boolean showHitBox = false;


    public PlayState(int level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            ui = loader.load();
            controller = loader.getController();

            controller.setLevel(level);

            gc = controller.getPlayCanvas().getGraphicsContext2D();

            explosionCanvasManager = new ExplosionCanvas(
                    Config.getScreenWidth(),
                    Config.getScreenHeight()
            );

            // Thêm canvas hiệu ứng nổ vào UI
            Pane rootPane = (Pane) controller.getPlayCanvas().getParent();
            rootPane.getChildren().add(explosionCanvasManager);


            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/ball/ballSpriteImage.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle/paddleSpriteImage.png").toExternalForm());

            paddle = new Paddle(Config.getStartPaddleX(), Config.getStartPaddleY(), Config.paddleWidth, Config.paddleHeight,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    Config.paddleHitBoxOffsetX, Config.paddleHitBoxOffsetY, Config.paddleHitBoxW, Config.paddleHitBoxH);

            ball = new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                    ballSprite, 10, 1, 880, 512, 0.1,
                    Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY, Config.ballHitBoxW, Config.ballHitBoxH);

            levelManager = new LevelManager();
            this.level = level;
            bricks = levelManager.loadLevel(level);

            balls.add(ball);

            SoundManager.getInstance().play(SoundType.BACKGROUND_MUSIC);

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

        ball = new Ball(Config.getStartBallX(), Config.getStartBallY(), Config.ballWidth, Config.ballHeight,
                ballSprite, 10, 1, 880, 512, 0.1,
                Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY, Config.ballHitBoxW, Config.ballHitBoxH);

        balls.clear();
        balls.add(ball);
        sidePaddles.clear();
        triplePaddleTimer[0] = 0;
        powerUps.clear();
    }

    private void spawnPowerUp(double x, double y) {
        if (Math.random() > Config.POWERUP_DROP_CHANCE) return;

        PowerUp powerUp = PowerUpFactory.createRandomPowerUp(x, y);
        powerUps.add(powerUp);
    }

    private void applyPowerUp(PowerUp powerUp) {
        PowerUpContext context = new PowerUpContext(
                balls, ball, paddle, sidePaddles,
                lives, timeSeconds, triplePaddleTimer
        );

        powerUp.applyEffect(context);

        lives = context.getLives();
        timeSeconds = context.getTimeSeconds();
    }

    @Override
    public void update() {
        double dt = Config.TARGET_DT; // 60 FPS cố định

        // ======== INPUT ========
        if (leftPressed) {
            paddle.setDx(-Config.paddleSpeed);
        } else if (rightPressed) {
            paddle.setDx(Config.paddleSpeed);
        } else {
            paddle.setDx(0);
        }

        startText.update(dt);
        if (startTime) timeSeconds += dt;
        int timeSecondsInt = (int) timeSeconds;
        healthText.update(lives, timeSecondsInt);

        // ======== UPDATE PADDLE ========
        double oldPaddleX = paddle.getX();
        paddle.update(dt);

        // Giới hạn paddle chính trong màn hình
        if (paddle.getX() < 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() > Config.getScreenWidth()) {
            paddle.setX(Config.getScreenWidth() - paddle.getWidth());
        }

        // Nếu có side paddles, kiểm tra thêm giới hạn cho chúng
        if (!sidePaddles.isEmpty()) {
            // Vị trí paddle trái dự kiến
            double leftPaddleX = paddle.getX() - Config.paddleHitBoxW - (Config.paddleHitBoxOffsetX * 2);
            if (leftPaddleX < 0) {
                paddle.setX(paddle.getX() - leftPaddleX);
            }

            // Vị trí paddle phải dự kiến
            double rightPaddleX = paddle.getX() + Config.paddleHitBoxW + (Config.paddleHitBoxOffsetX * 2);
            double rightPaddleMaxX = rightPaddleX + paddle.getWidth();
            if (rightPaddleMaxX > Config.getScreenWidth()) {
                paddle.setX(paddle.getX() - (rightPaddleMaxX - Config.getScreenWidth()));
            }
        }

        // ======== UPDATE ALL BALLS ========
        // FIX: Update TẤT CẢ balls, không chỉ ball chính
        for (Ball currentBall : balls) {
            if (currentBall.isHeld()) {
                currentBall.setX(currentBall.getX() + (paddle.getX() - oldPaddleX));
            } else {
                currentBall.update(dt);
            }
        }

        // ======== BALL - WALL COLLISION ========
        for (Ball currentBall : new ArrayList<>(balls)) {
            Rectangle2D ballBox = currentBall.getHitBox();

            if (ballBox.getMinX() <= 0) {
                currentBall.setX(currentBall.getX() - ballBox.getMinX());
                currentBall.setDx(-currentBall.getDx());

                SoundManager.getInstance().play(SoundType.BOUNCE);
            }

            if (ballBox.getMaxX() >= Config.getScreenWidth()) {
                currentBall.setX(currentBall.getX() - (ballBox.getMaxX() - Config.getScreenWidth()));
                currentBall.setDx(-currentBall.getDx());

                SoundManager.getInstance().play(SoundType.BOUNCE);
            }

            if (ballBox.getMinY() <= 0) {
                currentBall.setY(currentBall.getY() - ballBox.getMinY());
                currentBall.setDy(-currentBall.getDy());

                SoundManager.getInstance().play(SoundType.BOUNCE);
            }

            // check nếu ball nào rơi xuống dưới thì mất
            if (ballBox.getMinY() > Config.getScreenHeight()) {
                balls.remove(currentBall);
                continue;
            }
        }

        // Check Lost
        if (balls.isEmpty()) {
            lives -= 1;

            SoundManager.getInstance().play(SoundType.LOSE_BALL);
            resetBallPaddle();
            startTime = false;
        }


        // ======== BALL - BRICK COLLISION ========
        for (Ball currentBall : new ArrayList<>(balls)) {
            Rectangle2D ballBox = currentBall.getHitBox();

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

                double overlapLeft = ballRight - brickLeft;
                double overlapRight = brickRight - ballLeft;
                double overlapTop = ballBottom - brickTop;
                double overlapBottom = brickBottom - ballTop;

                boolean ballFromLeft = overlapLeft < overlapRight;
                boolean ballFromTop = overlapTop < overlapBottom;

                double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
                double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

                if (minOverlapX < minOverlapY) {
                    currentBall.setDx(-currentBall.getDx());

                    if (ballFromLeft)
                        currentBall.setX(brickLeft - currentBall.getWidth());
                    else
                        currentBall.setX(brickRight);
                } else {
                    currentBall.setDy(-currentBall.getDy());

                    if (ballFromTop)
                        currentBall.setY(brickTop - currentBall.getHeight());
                    else
                        currentBall.setY(brickBottom);
                }

                brick.takeDamage();

                if (brick.isDestroyed()) {
                    explosionCanvasManager.addExplosion(
                            brick.getX(),
                            brick.getY(),
                            brick.getWidth(),
                            brick.getHeight()
                    );


                    SoundManager.getInstance().play(SoundType.BRICK_BREAK);
                    spawnPowerUp(brick.getX() + brick.getWidth() / 2, brick.getY());
                } else {

                    SoundManager.getInstance().play(SoundType.BOUNCE);
                }

                break;
            }
        }

// ======== UPDATE POWERUPS ========
        for (PowerUp powerUp : new ArrayList<>(powerUps)) {
            powerUp.update(dt);


            if (powerUp.getHitBox().intersects(paddle.getHitBox())) {
                applyPowerUp(powerUp);
                powerUp.setCollected(true);

                SoundManager.getInstance().play(SoundType.POWER_UP);
            }


            if (!powerUp.isCollected()) {
                for (Paddle sidePaddle : sidePaddles) {
                    if (powerUp.getHitBox().intersects(sidePaddle.getHitBox())) {
                        applyPowerUp(powerUp);
                        powerUp.setCollected(true);

                        SoundManager.getInstance().play(SoundType.POWER_UP);
                        break; // Thoát khỏi vòng lặp side paddles
                    }
                }
            }

            if (powerUp.getY() > Config.getScreenHeight()) {
                powerUp.setCollected(true);
            }
        }
        powerUps.removeIf(PowerUp::isCollected);

        // ======== UPDATE SIDE PADDLES ========
        if (triplePaddleTimer[0] > 0) {
            triplePaddleTimer[0] -= dt;

            for (Paddle sidePaddle : sidePaddles) {
                sidePaddle.setDx(paddle.getDx());
                sidePaddle.update(dt);
                sidePaddle.setY(paddle.getY());
            }

            // TÍNH TOÁN ĐỂ HITBOX SÁT NHAU
            if (!sidePaddles.isEmpty()) {
                // Paddle trái: lùi lại đúng bằng chiều rộng hitbox + offset 2 bên
                sidePaddles.get(0).setX(
                        paddle.getX() - Config.paddleHitBoxW - (Config.paddleHitBoxOffsetX * 2)
                );

                // Paddle phải: tiến về phải đúng bằng chiều rộng hitbox + offset 2 bên
                sidePaddles.get(1).setX(
                        paddle.getX() + Config.paddleHitBoxW + (Config.paddleHitBoxOffsetX * 2)
                );
            }
        } else {
            sidePaddles.clear();
        }

        // ======== BALL - SIDE PADDLES COLLISION ========
        for (Ball currentBall : balls) {
            for (Paddle sidePaddle : sidePaddles) {
                if (currentBall.getHitBox().intersects(sidePaddle.getHitBox())) {
                    double ballCenterX = currentBall.getHitBox().getMinX() + currentBall.getHitBox().getWidth() / 2;
                    double ballCenterY = currentBall.getHitBox().getMinY() + currentBall.getHitBox().getHeight() / 2;
                    double paddleCenterX = sidePaddle.getHitBox().getMinX() + sidePaddle.getHitBox().getWidth() / 2;
                    double paddleCenterY = sidePaddle.getHitBox().getMinY() + sidePaddle.getHitBox().getHeight() / 2;

                    currentBall.setDx((ballCenterX - paddleCenterX) * Config.ballDxMultiple);
                    currentBall.setDy((ballCenterY - paddleCenterY) * Config.ballDyMultiple);


                    SoundManager.getInstance().play(SoundType.BOUNCE);
                }
            }
        }
    // ======== BALL - PADDLE COLLISION ========
        for (Ball currentBall : balls) {
            if (currentBall.getHitBox().intersects(paddle.getHitBox())) {
                double ballCenterX = currentBall.getHitBox().getMinX() + currentBall.getHitBox().getWidth() / 2;
                double ballCenterY = currentBall.getHitBox().getMinY() + currentBall.getHitBox().getHeight() / 2;
                double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
                double paddleCenterY = paddle.getHitBox().getMinY() + paddle.getHitBox().getHeight() / 2;

                currentBall.setDx((ballCenterX - paddleCenterX) * Config.ballDxMultiple);
                currentBall.setDy((ballCenterY - paddleCenterY) * Config.ballDyMultiple);


                if (!ball.isHeld()) SoundManager.getInstance().play(SoundType.BOUNCE);
            }
        }
        // ======== CHECK WIN ========
        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }

        if (allDestroyed) {
            if (level == MainApp.userManager.getCurrentUser().getLastLevel()) {
                MainApp.userManager.getCurrentUser().setLastLevel(level + 1);
            }
            MainApp.userManager.saveUsers();

            SoundManager.getInstance().play(SoundType.WIN);

            MainApp.stateStack.push(new WinLevelState(level));

            if (timeSecondsInt < MainApp.userManager.getCurrentUser().getLevelResult(level)){
                MainApp.userManager.getCurrentUser().setLevelResult(level, timeSecondsInt);
            }
        }

        // ======== CHECK LOSE ========
        if (lives == 0) {
            SoundManager.getInstance().play(SoundType.LOSE);
            MainApp.stateStack.push(new LoseLevelState(level));
        }
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, Config.getScreenWidth(), Config.getScreenHeight());

        startText.render(gc);
        healthText.render(gc);

        for (Ball currentBall : balls) {
            currentBall.render(gc);
        }

        paddle.render(gc);

        for (Paddle sidePaddle : sidePaddles) {
            sidePaddle.render(gc);
        }

        for (Brick brick : bricks) {
            brick.render(gc);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.render(gc);
        }

        // FIX: Hiển thị hitbox cho TẤT CẢ balls và side paddles
        if (showHitBox) {
            for (Ball currentBall : balls) {
                currentBall.showHitBox(gc);
            }
            paddle.showHitBox(gc);

            // FIX: Thêm hitbox cho side paddles
            for (Paddle sidePaddle : sidePaddles) {
                sidePaddle.showHitBox(gc);
            }
        }
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            rightPressed = true;
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            MainApp.stateStack.push(new PauseState(level));
        }
        if (event.getCode() == KeyCode.H) {
            showHitBox = !showHitBox;
        }

        if (event.getCode() == KeyCode.SPACE) {
            System.out.println("Space pressed");
            ball.stopHolding();
            startText.hide();
            startTime = true;
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            leftPressed = false;
        }
        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            rightPressed = false;
        }
    }
}