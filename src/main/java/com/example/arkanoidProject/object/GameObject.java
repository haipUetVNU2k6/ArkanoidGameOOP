package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.canvas.GraphicsContext;

public class GameObject {
    protected double x, y;
    protected double width, height;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(double dt) {}

    public void render(GraphicsContext gc) {}

    // Getter / Setter
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }

}
