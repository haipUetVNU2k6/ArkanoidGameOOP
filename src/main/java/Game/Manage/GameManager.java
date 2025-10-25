package Game.Manage;

import Game.Level.Level;
import Game.Object.Ball;
import Game.Object.Brick.Brick;
import Game.Object.Bullet;
import Game.Object.Paddle;
import Game.Object.PowerUp.PowerUp;
import Game.Object.PowerUp.PowerUpFactory;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {
    public static boolean start = false;
    private Level level;

    private static final GameManager instance = new GameManager();
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private int scores;
    public static int lives;
    private GameState gameState;

    public static final double HEIGHT = 600;
    public static final double WIDTH = 800;

    private long lastShootTime = 0;
    private final long shootDelay = 250; // mili giây giữa 2 viên đạn

    private GameManager() {
        gameState = GameState.MENU;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        GAME_OVER,
        WIN,
        SETTINGS;
    }

    public void startGame() {
        bricks.clear();
        powerUps.clear();
        bullets.clear();

        gameState = GameState.PLAYING;
        paddle = new Paddle(Paddle.startX, Paddle.startY, Paddle.WIDTH, Paddle.HEIGHT, 3);
        ball = new Ball(Ball.startX, Ball.startY, Ball.r, 1.3, 0, 0);
        scores = 0;
        lives = 3;

        level = new Level(1);
        String path = "/MatrixLevel/matrix" + level.getId() + ".txt";
        level.setPath(path);
        level.loadLevel();
        bricks = level.getBricks();
        start = false;
    }

    public void updateGame() {
        if (gameState != GameState.PLAYING) return;

        paddle.update();
        ball.update();
        checkCollision();

        updateBullets();
        updatePowerUps();

        // Khi thắng màn
        if (bricks.isEmpty()) {
            if (level.getId() >= Level.maxID) gameState = GameState.WIN;
            else {
                level.setId(level.getId() + 1);
                String path = "/MatrixLevel/matrix" + level.getId() + ".txt";
                level.setPath(path);
                level.loadLevel();
                paddle.reset();
                ball.reset();
                start = false;
            }
        }
    }

    private void updateBullets() {
        // Nếu paddle đang có power-up SHOOTING_PADDLE thì bắn
        if (paddle.getCurrentPowerUp() == PowerUp.Type.SHOOTING_PADDLE) {
            long now = System.currentTimeMillis();
            if (now - lastShootTime >= shootDelay) {
                lastShootTime = now;

                // Sinh đạn ngay giữa paddle
                double bx = paddle.getX() + paddle.getWidth() / 2 - 2;
                double by = paddle.getY() - 10;
                bullets.add(new Bullet(bx, by, 4, 8, 0, -1)); // tốc độ bay lên 8px/frame
            }
        }

        // Cập nhật vị trí đạn và xử lý va chạm
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.move();

            // Đạn bay khỏi màn hình
            if (b.getY() + b.getHeight() < 0) {
                it.remove();
                continue;
            }

            // Va chạm với gạch (không cần collidesWith)
            Iterator<Brick> brickIt = bricks.iterator();
            boolean hit = false;
            while (brickIt.hasNext()) {
                Brick brick = brickIt.next();

                if (b.getX() < brick.getX() + brick.getWidth() &&
                        b.getX() + b.getWidth() > brick.getX() &&
                        b.getY() < brick.getY() + brick.getHeight() &&
                        b.getY() + b.getHeight() > brick.getY()) {

                    brick.takeHit(1);
                    it.remove();
                    hit = true;

                    if (brick.isDestroyed()) {
                        PowerUp newPower = PowerUpFactory.createRandomPowerUp(brick.getX(), brick.getY());
                        if (newPower != null) powerUps.add(newPower);
                        brickIt.remove();
                        scores += 10;
                    }
                    break;
                }
            }

            if (hit) continue; // nếu đạn đã trúng gạch thì dừng xét
        }
    }

    private void updatePowerUps() {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            p.update();

            // Rơi khỏi màn hình
            if (p.getY() > HEIGHT) {
                it.remove();
                continue;
            }

            // Paddle ăn power-up
            if (p.collidesWith(paddle)) {
                paddle.setCurrentPowerUp(p.getType());
                it.remove();
            }
        }
    }

    public void checkCollision() {
        // Ball va Paddle
        if (start) {
            ball.bounceOf(paddle);
        } else {
            double diff = paddle.getX() - paddle.getOldX();
            if (diff != 0) ball.setX(ball.getX() + diff);
        }

        // Ball va Brick
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            ball.bounceOf(brick);
            if (ball.isCollision) {
                brick.takeHit(1);
                ball.isCollision = false;
            }
            if (brick.isDestroyed()) {
                PowerUp newPower = PowerUpFactory.createRandomPowerUp(brick.getX(), brick.getY());
                if (newPower != null) powerUps.add(newPower);
                iterator.remove();
                scores += 10;
            }
        }

        // Ball rơi xuống đáy
        if (ball.getY() >= HEIGHT) {
            if (lives > 0) {
                paddle.reset();
                ball.reset();
                start = false;
                lives--;
            } else {
                gameState = GameState.GAME_OVER;
            }
        }
    }

    public void render(GraphicsContext gc) {
        paddle.render(gc);
        ball.render(gc);

        for (Brick b : bricks) b.render(gc);
        for (PowerUp p : powerUps) p.render(gc);
        for (Bullet b : bullets) b.render(gc);
    }

    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public ArrayList<Brick> getBricks() { return bricks; }
    public int getScore() { return scores; }
    public int getLives() { return lives; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    public GameState getGameState() { return gameState; }
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
    // --- Thêm vào cuối class GameManager ---
    public void spawnExtraBalls() {
        // tạo thêm 1 bóng ở vị trí hiện tại của bóng chính
        Ball newBall = new Ball(
                ball.getX(),
                ball.getY(),
                Ball.r,
                ball.getSpeed(),
                -ball.getDirectionX(),
                -ball.getDirectionY()
        );
        // nếu bạn dùng danh sách balls, thêm vào đây:
        // balls.add(newBall);
        // nếu chỉ có 1 ball duy nhất, tạm thời ta chỉ cho bóng chính đảo hướng:
        ball.setDirectionX(-ball.getDirectionX());
        ball.setDirectionY(-ball.getDirectionY());
    }

    private boolean shootingMode = false;
    private long shootingStartTime;

    public void enableShootingMode(boolean enable) {
        this.shootingMode = enable;
        this.shootingStartTime = System.currentTimeMillis();

        if (!enable) {
            // tắt chế độ bắn: xóa đạn còn lại
            bullets.clear();
        }
    }

}
