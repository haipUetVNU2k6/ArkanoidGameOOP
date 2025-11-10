package com.example.arkanoidProject.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ExplosionCanvas extends Canvas {

    private static class Particle {
        double x, y, vx, vy;
        double life = 1.0;
        double size;

        Particle(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = 2 + Math.random() * 3;
        }

        void update() {
            x += vx;
            y += vy;
            vx *= 0.98;
            vy += 0.2; // Gravity
            life -= 0.025;
            if (life < 0) life = 0;
        }

        void render(GraphicsContext gc) {
            if (life <= 0) return;

            gc.save();
            gc.setGlobalAlpha(life);

            // Particle màu vàng cam
            gc.setFill(Color.ORANGE);
            gc.fillOval(x - size/2, y - size/2, size, size);

            // Glow effect
            gc.setFill(Color.YELLOW.deriveColor(1, 1, 1, life * 0.3));
            gc.fillOval(x - size * 1.5, y - size * 1.5, size * 3, size * 3);

            gc.restore();
        }
    }

    private static class ShockWave {
        double x, y;
        double radius = 0;
        double maxRadius = 50;
        double life = 1.0;

        ShockWave(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void update() {
            radius += 4;
            life -= 0.06;
            if (life < 0) life = 0;
        }

        void render(GraphicsContext gc) {
            if (life <= 0 || radius > maxRadius) return;

            gc.save();
            gc.setGlobalAlpha(life * 0.5);

            // Vòng sóng màu trắng
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

            gc.restore();
        }
    }

    private static class Explosion {
        List<Particle> particles = new ArrayList<>();
        ShockWave shockWave;
        int frame = 0;
        static final int DURATION = 40;

        Explosion(double x, double y, double width, double height) {
            double centerX = x + width / 2;
            double centerY = y + height / 2;

            // Tạo 15-20 particles bay tứ tung
            int count = 15 + (int)(Math.random() * 6);
            for (int i = 0; i < count; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double speed = 2 + Math.random() * 4;
                particles.add(new Particle(
                        centerX, centerY,
                        Math.cos(angle) * speed,
                        Math.sin(angle) * speed
                ));
            }

            // Tạo shock wave
            shockWave = new ShockWave(centerX, centerY);
        }

        void update() {
            if (frame < DURATION) {
                frame++;
                particles.forEach(Particle::update);
                shockWave.update();
            }
        }

        void render(GraphicsContext gc) {
            if (frame >= DURATION) return;
            shockWave.render(gc);
            particles.forEach(p -> p.render(gc));
        }

        boolean isFinished() {
            return frame >= DURATION;
        }
    }

    private final List<Explosion> explosions = new ArrayList<>();

    public ExplosionCanvas(double width, double height) {
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

    // Method gọi khi brick bị phá - không cần color parameter
    public void addExplosion(double x, double y, double width, double height) {
        explosions.add(new Explosion(x, y, width, height));
    }

    private void update() {
        explosions.forEach(Explosion::update);
        explosions.removeIf(Explosion::isFinished);
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        for (Explosion exp : explosions) {
            exp.render(gc);
        }
    }
}