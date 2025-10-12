package com.example.arkanoidgameoop.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ball extends MovableObject {
    private Image ballImage;

    public Ball(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height, dx, dy);

        ballImage = new Image(getClass().getResourceAsStream("/com/example/arkanoidgameoop/images/ball/ball.png"));
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(ballImage, x, y, width, height);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }
}
