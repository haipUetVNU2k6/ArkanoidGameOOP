package com.example.arkanoidProject.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {
    public static final double WIDTH = 100;
    public static final double HEIGHT = 15;
    public static final double SPEED = 5;

    public Paddle(double x, double y) {
        Rectangle rect = new Rectangle(WIDTH, HEIGHT);
        rect.setFill(Color.BLUE);
        node = rect;
        setX(x);
        setY(y);
    }

    public void moveLeft() {
        dx = -SPEED;
    }

    public void moveRight() {
        dx = SPEED;
    }

    public void stop() {
        dx = 0;
    }

    public Rectangle getRectangle() {
        return (Rectangle) node;
    }
}
