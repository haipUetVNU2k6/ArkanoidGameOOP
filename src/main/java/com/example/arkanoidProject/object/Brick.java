package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class Brick extends GameObject {
    private int health;

    private boolean destroyed = false;

    protected Image currentImage;

    private BrickSkin brickSkin;

    private int damage = 0;

    public Brick(double x, double y, double width, double height, BrickSkin brickSkin, int health) {
        super(x, y, width, height);
        this.brickSkin = brickSkin;
        this.health = health;
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        currentImage = brickSkin.getTexture(damage);
        if (!destroyed && currentImage != null) {
            gc.drawImage(currentImage, x, y, width, height);
        }
    }

    public void destroy() {
        destroyed = true;
        // Có thể thêm hiệu ứng animation khi brick bị phá
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void takeDamage() {
        damage++;
        health--;
        if (health == 0) destroy();
    }

    @Override
    public  void update(double dt) {}

    @Override
    public void reset() {}
}
