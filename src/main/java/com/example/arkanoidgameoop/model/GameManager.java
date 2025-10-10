package com.example.arkanoidgameoop.model;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameManager {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    public Ball ball;
    public Paddle paddle;
    public List<Brick> bricks = new ArrayList<>();
    public int score = 0;
    public boolean gameOver = false;

    public GameManager() {
        reset();
    }

    public void reset(){
        // paddle centered at bottom
        paddle = new Paddle(WIDTH/2 - 50, HEIGHT - 60, 100, 20);
        // ball above paddle
        ball = new Ball(paddle.x + paddle.width/2 - 8, paddle.y - 16, 8);
        bricks.clear();
        score = 0;
        gameOver = false;
        createBricks();
    }

    private void createBricks() {
        int cols = 8;
        int rows = 5;
        double brickW = (WIDTH - 40) / cols;
        double brickH = 20;
        double startX = 20;
        double startY = 60;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * brickW;
                double y = startY + r * (brickH + 8);
                bricks.add(new Brick(x, y, brickW - 6, brickH));
            }
        }
    }

    // produce JavaFX Node for ball/paddle/brick (simple approach)
    public Node nodeForBall() {
        Circle c = new Circle(ball.x + ball.radius, ball.y + ball.radius, ball.radius);
        c.setId("ball");
        return c;
    }
    public Node nodeForPaddle() {
        Rectangle r = new Rectangle(paddle.x, paddle.y, paddle.width, paddle.height);
        r.setId("paddle");
        return r;
    }
    public List<Node> nodesForBricks() {
        List<Node> list = new ArrayList<>();
        for (Brick b : bricks) {
            Rectangle r = new Rectangle(b.x, b.y, b.width, b.height);
            r.setId("brick");
            list.add(r);
        }
        return list;
    }

    // update physics: dt in seconds
    public void update(double dt) {
        if (gameOver) return;
        ball.updatePosition(dt);

        // bounce off walls
        if (ball.x <= 0) {
            ball.x = 0;
            ball.vx = Math.abs(ball.vx);
        } else if (ball.x + ball.width >= WIDTH) {
            ball.x = WIDTH - ball.width;
            ball.vx = -Math.abs(ball.vx);
        }
        if (ball.y <= 0) {
            ball.y = 0;
            ball.vy = Math.abs(ball.vy);
        }
        // bottom -> lose life / game over
        if (ball.y + ball.height >= HEIGHT) {
            gameOver = true;
        }

        // paddle collision (simple AABB)
        if (rectCircleCollide(paddle.x, paddle.y, paddle.width, paddle.height,
                ball.x + ball.radius, ball.y + ball.radius, ball.radius)) {
            ball.y = paddle.y - ball.height;
            ball.vy = -Math.abs(ball.vy);
            // change vx depending on where hits paddle
            double hitPos = (ball.x + ball.radius) - (paddle.x + paddle.width/2);
            ball.vx += hitPos * 2;
        }

        // bricks collision
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick b = it.next();
            if (rectCircleCollide(b.x, b.y, b.width, b.height,
                    ball.x + ball.radius, ball.y + ball.radius, ball.radius)) {
                // simple response: invert vy and remove brick
                ball.vy = -ball.vy;
                it.remove();
                score += 10;
                break; // only one brick per frame
            }
        }

        // if no bricks -> game over (win)
        if (bricks.isEmpty()) {
            gameOver = true;
        }
    }

    private boolean rectCircleCollide(double rx, double ry, double rw, double rh,
                                      double cx, double cy, double cr) {
        double nearestX = clamp(cx, rx, rx + rw);
        double nearestY = clamp(cy, ry, ry + rh);
        double dx = cx - nearestX;
        double dy = cy - nearestY;
        return (dx*dx + dy*dy) <= cr*cr;
    }

    private double clamp(double val, double a, double b) {
        return Math.max(a, Math.min(b, val));
    }
}
