package com.example.arkanoidProject.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class ParticleCanvas extends Canvas {

    private static class Particle {
        double x, y;
        double size;
        double speed;
        double opacity;

        Particle(double x, double y, double size, double speed, double opacity) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.opacity = opacity;
        }
    }

    private final Particle[] particles;
    private final Random random = new Random();

    public ParticleCanvas(double width, double height, int count) {
        super(width, height);

        particles = new Particle[count];
        for (int i = 0; i < count; i++) {
            particles[i] = createRandomParticle(width, height);
        }

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double delta = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                update(delta);
                draw();
            }
        };
        timer.start();
    }

    private Particle createRandomParticle(double width, double height) {
        double x = random.nextDouble() * width;
        double y = random.nextDouble() * height;
        double size = 1.0 + random.nextDouble() * 2.0;
        double speed = 10 + random.nextDouble() * 40;
        double opacity = 0.2 + random.nextDouble() * 0.6;
        return new Particle(x, y, size, speed, opacity);
    }

    private void update(double delta) {
        double width = getWidth();
        double height = getHeight();

        for (Particle p : particles) {
            p.y -= p.speed * delta;
            if (p.y < 0) {
                p.x = random.nextDouble() * width;
                p.y = height + p.size;
                p.size = 1.0 + random.nextDouble() * 2.0;
                p.speed = 10 + random.nextDouble() * 40;
                p.opacity = 0.2 + random.nextDouble() * 0.6;
            }
        }
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        double width = getWidth();
        double height = getHeight();

        // Rất trong suốt, không xóa nền để thấy ảnh nền tĩnh phía dưới
        gc.clearRect(0, 0, width, height);

        // Vẽ các điểm sáng
        for (Particle p : particles) {
            gc.setFill(Color.rgb(0, 235, 255, p.opacity));
            gc.fillOval(p.x, p.y, p.size, p.size);

            // Glow nhẹ
            gc.setFill(Color.rgb(0, 235, 255, p.opacity * 0.3));
            gc.fillOval(p.x - p.size, p.y - p.size, p.size * 3, p.size * 3);
        }
    }
}
