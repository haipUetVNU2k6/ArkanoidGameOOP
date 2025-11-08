package com.example.arkanoidProject.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class RainCanvas extends Canvas {

    private static class Raindrop {
        double x, y, length, speed, thickness, alpha;
    }

    private final List<Raindrop> drops = new ArrayList<>();

    // Thông số dễ chỉnh
    private static final double BASE_DENSITY = 0.4;   // mưa thưa hơn
    private static final double MIN_SPEED = 0.5;
    private static final double MAX_SPEED = 2.2;
    private static final double MIN_LENGTH = 4.0;
    private static final double MAX_LENGTH = 10.0;

    public RainCanvas(double width, double height) {
        super(width, height);
        setMouseTransparent(true);
        setStyle("-fx-background-color: transparent;");

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
            }
        }.start();
    }

    private void spawnRain() {
        // Random một chút số lượng mỗi frame để tránh cảm giác "máy móc"
        int count = (int) (Math.random() * 3 * BASE_DENSITY);
        for (int i = 0; i < count; i++) {
            Raindrop r = new Raindrop();
            r.x = Math.random() * getWidth();
            r.y = -10 - Math.random() * 30;

            // Giọt xa thì mảnh và mờ, giọt gần thì to và rõ
            double depth = Math.random(); // 0 = xa, 1 = gần
            r.length = MIN_LENGTH + depth * (MAX_LENGTH - MIN_LENGTH);
            r.speed = MIN_SPEED + depth * (MAX_SPEED - MIN_SPEED);
            r.thickness = 0.5 + depth * 1.2;
            r.alpha = 0.2 + depth * 0.5;

            drops.add(r);
        }
    }

    private void update() {
        spawnRain();

        List<Raindrop> alive = new ArrayList<>();
        for (Raindrop r : drops) {
            r.y += r.speed;

            if (r.y < getHeight()) alive.add(r);
        }
        drops.clear();
        drops.addAll(alive);
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        for (Raindrop r : drops) {
            gc.setStroke(Color.rgb(160, 190, 255, r.alpha));
            gc.setLineWidth(r.thickness);
            gc.strokeLine(r.x, r.y, r.x, r.y + r.length);
        }
    }
}
