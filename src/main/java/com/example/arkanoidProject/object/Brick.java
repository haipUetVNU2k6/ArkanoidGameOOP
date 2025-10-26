package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class Brick extends GameObject {

    private boolean destroyed = false;
    private int hitPoints;
    private int id;

    public Brick(double x, double y, double width, double height, Image spriteSheet, int frameWidth, int frameHeight,
                 int columns, int rows, double frameDuration, int hitPoints, int id) {
        super(x, y, width, height,
                new SpriteAnimation(spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration));
        this.hitPoints = hitPoints;
        this.id = id;
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        if (!destroyed) {
            super.render(gc);
        }
    }

    public void destroy() {
       if(hitPoints <= 0) {
           destroyed = true;
       }
        // Có thể thêm hiệu ứng animation khi brick bị phá
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void takeHit(int amount) {
        hitPoints = hitPoints - amount;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public void reset() {

    }
}
