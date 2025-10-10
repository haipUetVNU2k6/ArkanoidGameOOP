package com.example.arkanoidgameoop.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveTo(double newX) {
        this.x = newX;
    }
}

//public class Paddle extends MovableObject {
//
//    private int speed;
//
//    public Paddle(double x, double y, int width, int height, int speed) {
//        super(x, y, width, height, 0, 0);
//        this.speed = speed;
//    }
//
//    @Override
//    public void update() {
//        move();
//
//        if (x < 0) x = 0;
//        if (x + width > GameManager.WIDTH) x = GameManager.WIDTH - width;
//    }
//
//    @Override
//    public void render(GraphicsContext gc) {
//        gc.setFill(Color.BLUE);
//        gc.fillRect(x, y, width, height);
//    }
//
//    public void moveLeft() {
//        dx = -speed;
//        move();
//        dx = 0;
//    }
//
//    public void moveRight() {
//        dx = speed;
//        move();
//        dx = 0;
//    }
//
//    public Rectangle2D getBounds() {
//        return new Rectangle2D(x, y, width, height);
//    }
//}
