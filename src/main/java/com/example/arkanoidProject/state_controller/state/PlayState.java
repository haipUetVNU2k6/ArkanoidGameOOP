package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
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
    private Pane root;
    private PlayCtrl controller;

    private GraphicsContext gc;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();

    private final int WIDTH = MainApp.WIDTH;
    private final int HEIGHT = MainApp.HEIGHT;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private long lastTime = 0;

    private LevelManager levelManager;
    private int level = 1;

    private int levelToLoad;


    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            root = loader.load();
            controller = loader.getController();

            gc = controller.getGameCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/cry.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddleAnimation.png").toExternalForm());

            ball = new Ball(300, 400, 36, 21,
                    ballSprite, 10, 1, 880, 512, 0.1,
                    18, 3, 18, 15);
            paddle = new Paddle(50, 720, 60, 72,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    MainApp.WIDTH, 3, 0, 780, 200);


            Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick.png").toExternalForm());
            levelManager = new LevelManager(brickSprite);
            int levelToLoad = MainApp.userManager.getCurrentUser().getLastLevel();
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

        if (leftPressed) {
            paddle.setDx(-400);
        } else if (rightPressed) {
            paddle.setDx(400);
        } else {
            paddle.setDx(0);
        }

        ball.update(dt);
        paddle.update(dt);
        // collision checks...
        //Ball-wall collison
        if (ball.getHitBox().getMinX() <= 0) ball.setDx(- ball.getDx());
        if (ball.getHitBox().getMaxX() >= WIDTH) ball.setDx(- ball.getDx());
        if (ball.getHitBox().getMinY() <= 0) ball.setDy(- ball.getDy());
        if (ball.getHitBox().getMaxY() >= HEIGHT) ball.setDy(- ball.getDy());

        if (ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() + ball.getHeight() > paddle.getY() &&
                ball.getY() < paddle.getY() + paddle.getHeight()) {

            // 🔹 Tính vị trí tương đối của tâm bóng so với tâm paddle
            double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
            double ballCenter = ball.getX() + ball.getWidth() / 2;
            double relativeIntersect = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

            // 🔹 Giới hạn góc bật từ -60° đến +60°
            double maxAngle = 90 * Math.PI / 180; // 60 độ → radian
            double bounceAngle = relativeIntersect * maxAngle;

            // 🔹 Tính vận tốc mới (vẫn giữ tốc độ tổng)
            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
            ball.setDx(speed * Math.sin(bounceAngle));
            ball.setDy(-Math.abs(speed * Math.cos(bounceAngle))); // bật lên

            // Đảm bảo bóng không dính paddle
            ball.setY(paddle.getY() - ball.getHeight() - 1);
        }

        for (Brick brick : bricks) {
            if (!brick.isDestroyed() &&
                    ball.getHitBox().getMinX() < brick.getX() + brick.getWidth() &&
                    ball.getHitBox().getMaxX() > brick.getX() &&
                    ball.getHitBox().getMinY() < brick.getY() + brick.getHeight() &&
                    ball.getHitBox().getMaxY() > brick.getY()) {

                // Xác định hướng va chạm
                double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
                double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight() / 2;

                double brickCenterX = brick.getX() + brick.getWidth() / 2;
                double brickCenterY = brick.getY() + brick.getHeight() / 2;

                double dx = ballCenterX - brickCenterX;
                double dy = ballCenterY - brickCenterY;

                // So sánh độ chênh để quyết định va trúng cạnh nào
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Va chạm trái/phải
                    ball.setDx(-ball.getDx());
                } else {
                    // Va chạm trên/dưới
                    ball.setDy(-ball.getDy());
                }

                brick.destroy();
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
                ball.resetPosition(300, 400); // bạn cần thêm hàm reset trong Ball
                paddle.setX(250);
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
        if (event.getCode() == KeyCode.H) {
            Ball.showHitbox = !Ball.showHitbox; // ✅ bật/tắt hitbox
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
