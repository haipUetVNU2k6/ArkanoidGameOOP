package com.example.arkanoidProject.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {
    public static final double WIDTH = 60;
    public static final double HEIGHT = 20;

    public Brick(double x, double y, Color color) {
        Rectangle rect = new Rectangle(WIDTH, HEIGHT);
        rect.setFill(color);
        node = rect;
        setX(x);
        setY(y);
    }

    public Rectangle getRectangle() {
        return (Rectangle) node;
    }
}
