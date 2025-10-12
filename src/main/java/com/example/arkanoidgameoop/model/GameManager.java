package com.example.arkanoidgameoop.model;

import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {

    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;

    private int scores;
    private int lives;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    public GameManager() {
        bricks = new ArrayList<>();
        scores = 0;
        lives = 3;
    }

    public void startGame() {
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 40, 100, 20, 7);
        ball = new Ball(WIDTH / 2, HEIGHT - 60, 40, 40, 1, -1);

        bricks.clear();
        int brickWidth = 50;
        int brickHeight = 20;
        int padding = 5;
        int rows = 5;
        int cols = 10;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * (brickWidth + padding) + 35;
                int y = row * (brickHeight + padding) + 50;
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
    }

    public void updateGame() {
        ball.update();

        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= WIDTH) {
            ball.setDx(-ball.getDx());
        }

        if (ball.getY() <= 0) {
            ball.setDy(-ball.getDy());
        }

        if (ball.getY() > HEIGHT) {
            lives--;
            resetBallAndPaddle();
        }

        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.setDy(-ball.getDy()); // chỉ đảo hướng Y

//            // tạo hiệu ứng góc bắn khác nhau tùy vị trí chạm
//            double hitPos = (ball.getX() + ball.getWidth() / 2)
//                    - (paddle.getX() + paddle.getWidth() / 2);
//            ball.setDx(hitPos * 0.1); // không đổi tốc độ, chỉ đổi hướng
        }

        Iterator<Brick> iter = bricks.iterator();
        while (iter.hasNext()) {
            Brick brick = iter.next();
            if (ball.getBounds().intersects(brick.getBounds())) {
                iter.remove();
                scores += 10;
                ball.setDy(-ball.getDy());
                break;
            }
        }

        paddle.update();
    }

    private void resetBallAndPaddle() {
        paddle.setX(WIDTH / 2 - paddle.getWidth() / 2);
        paddle.setY(HEIGHT - 40);
        ball.setX(WIDTH / 2);
        ball.setY(HEIGHT - 60);
        ball.setDx(1);
        ball.setDy(-1);
    }

    // Getters
    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public ArrayList<Brick> getBricks() { return bricks; }
    public int getScores() { return scores; }
    public int getLives() { return lives; }
}
