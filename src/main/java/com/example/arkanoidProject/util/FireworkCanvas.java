package com.example.arkanoidProject.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class FireworkCanvas extends Canvas {

    private static class Particle {
        double x, y, dx, dy, life;
        double size;
        Color color;
    }

    private final List<Particle> particles = new ArrayList<>();

    public FireworkCanvas(double width, double height) {
        super(width, height);
        setMouseTransparent(true);
        setStyle("-fx-background-color: transparent;");

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                spawnFountainParticles();
                draw();
            }
        }.start();
    }

    // màu cam – vàng – trắng sáng kiểu pháo bông
    private Color randomFireColor() {
        double hue = 25 + Math.random() * 30; // vàng cam
        double brightness = 0.8 + Math.random() * 0.2;
        return Color.hsb(hue, 1, brightness);
    }

    private void spawnFountainParticles() {
        double leftX = 20;
        double rightX = getWidth() - leftX;
        double baseY = getHeight() - 10;

        for (int i = 0; i < 12; i++) {  // số tia phun mỗi frame
            addFountainParticle(leftX, baseY, true);
            addFountainParticle(rightX, baseY, false);
        }
    }

    private void addFountainParticle(double x, double y, boolean left) {
        Particle p = new Particle();
        p.x = x;
        p.y = y;

        // góc xòe ra (fan spread)
        double angle = (left ? -1.1 : -2.0) + (Math.random() * 0.6 - 0.3);
        double speed = 3 + Math.random() * 3;

        p.dx = Math.cos(angle) * speed;
        p.dy = Math.sin(angle) * speed;
        p.life = 1.8 + Math.random() * 0.4;
        p.size = 2 + Math.random() * 2;
        p.color = randomFireColor();

        particles.add(p);
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        List<Particle> alive = new ArrayList<>();

        for (Particle p : particles) {
            p.life -= 0.02;

            p.x += p.dx;
            p.y += p.dy;
            p.dy += 0.04; // gravity rơi xuống

            if (p.life > 0) {
                alive.add(p);
                Color c = p.color.deriveColor(1, 1, 1, p.life);

                gc.setFill(c);
                gc.fillOval(p.x, p.y, p.size, p.size);

                // glow sáng
                gc.setFill(c.deriveColor(1, 1, 1, p.life * 0.6));
                gc.fillOval(p.x - p.size, p.y - p.size, p.size * 2.5, p.size * 2.5);
            }
        }

        particles.clear();
        particles.addAll(alive);
    }
}
