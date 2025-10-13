package com.example.arkanoidProject.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovableObject {
    public static final double RADIUS = 10;

    public Ball(double x, double y) {
        Circle circle = new Circle(RADIUS);
        circle.setFill(Color.WHITE);
        node = circle;
        setX(x);
        setY(y);

        // Initial direction
        dx = 3;
        dy = -3;
    }

    public void bounceX() {
        dx = -dx;
    }

    public void bounceY() {
        dy = -dy;
    }

    public Circle getCircle() {
        return (Circle) node;
    }
}
