package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class Brick extends GameObject {

    private boolean destroyed = false;

    protected Image image;

    public Brick(double x, double y, double width, double height, Image image) {
        super(x, y, width, height);
        this.image = image;
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        if (!destroyed && image != null) {
            gc.drawImage(image, x, y, width, height);
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
