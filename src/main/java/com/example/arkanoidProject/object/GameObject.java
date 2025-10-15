package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.canvas.GraphicsContext;

public class GameObject {
    protected double x, y;
    protected double width, height;

    protected SpriteAnimation spriteAnimation;

    public GameObject(double x, double y, double width, double height, SpriteAnimation spriteAnimation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteAnimation = spriteAnimation;
    }

    public void update(double dt) {
        if (spriteAnimation != null) {
            spriteAnimation.update(dt);
        }
    }

    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY); // tránh méo hình
            spriteAnimation.render(gc, x, y, scale, scale);
        }
    }

    // Getter / Setter
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }

    public SpriteAnimation getSpriteAnimation() { return spriteAnimation; }
    public void setSpriteAnimation(SpriteAnimation spriteAnimation) {
        this.spriteAnimation = spriteAnimation;
    }
}
