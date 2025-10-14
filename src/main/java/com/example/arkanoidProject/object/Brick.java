package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class Brick extends GameObject {

    private boolean destroyed = false;

    public Brick(double x, double y, double width, double height, Image spriteSheet, int frameCount, int frameWidth, int frameHeight, double frameDuration) {
        super(x, y, width, height,
                new SpriteAnimation(spriteSheet, frameWidth, frameHeight, frameCount, 1, frameDuration));
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        if (!destroyed) {
            super.render(gc);
        }
    }

    public void destroy() {
        destroyed = true;
        // Có thể thêm hiệu ứng animation khi brick bị phá
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
