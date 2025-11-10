package com.example.arkanoidProject.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class ExplosionCanvas extends Canvas {

    // Particle lửa
    private static class FireParticle {
        double x, y, vx, vy;
        double life = 1.0;
        double size;
        Color color;

        FireParticle(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = 4 + Math.random() * 4;
            double rand = Math.random();
            if(rand < 0.4) color = Color.RED;
            else if(rand < 0.8) color = Color.ORANGE;
            else color = Color.YELLOW;
        }

        void update() {
            x += vx;
            y += vy;
            vx *= 0.9;
            vy += 0.2; // gravity
            life -= 0.03;
            if(life < 0) life = 0;
        }

        void render(GraphicsContext gc) {
            if(life <= 0) return;
            gc.save();
            gc.setGlobalAlpha(life);
            gc.setFill(color);
            gc.fillOval(x - size/2, y - size/2, size, size);
            gc.restore();
        }
    }

    // Tia lửa bắn ra xung quanh
    private static class Spark {
        double x, y, vx, vy;
        double life = 1.0;
        double size;

        Spark(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = 2 + Math.random() * 2;
        }

        void update() {
            x += vx;
            y += vy;
            vx *= 0.92;
            vy += 0.15; // gravity nhẹ
            life -= 0.04;
            if(life < 0) life = 0;
        }

        void render(GraphicsContext gc) {
            if(life <= 0) return;
            gc.save();
            gc.setGlobalAlpha(life);
            gc.setFill(Color.YELLOW);
            gc.fillOval(x - size/2, y - size/2, size, size);
            gc.restore();
        }
    }

    // Khói bay lên
    private static class Smoke {
        double x, y;
        double vy;
        double life = 1.0;
        double size;

        Smoke(double x, double y) {
            this.x = x + (Math.random()-0.5)*10;
            this.y = y;
            this.vy = -0.5 - Math.random()*0.5;
            this.size = 10 + Math.random()*10;
        }

        void update() {
            y += vy;
            life -= 0.02;
            if(life < 0) life = 0;
        }

        void render(GraphicsContext gc) {
            if(life <= 0) return;
            gc.save();
            gc.setGlobalAlpha(life*0.6);
            gc.setFill(Color.GRAY);
            gc.fillOval(x - size/2, y - size/2, size, size);
            gc.restore();
        }
    }

    private static class Explosion {
        List<FireParticle> fireParticles = new ArrayList<>();
        List<Spark> sparks = new ArrayList<>();
        List<Smoke> smokes = new ArrayList<>();
        int frame = 0;
        static final int DURATION = 50;

        Explosion(double x, double y) {
            int fireCount = 25 + (int)(Math.random()*10);
            for(int i=0;i<fireCount;i++){
                double angle = Math.random()*2*Math.PI;
                double speed = 2 + Math.random()*4;
                fireParticles.add(new FireParticle(x, y, Math.cos(angle)*speed, Math.sin(angle)*speed));
            }

            int sparkCount = 15 + (int)(Math.random()*10);
            for(int i=0;i<sparkCount;i++){
                double angle = Math.random()*2*Math.PI;
                double speed = 3 + Math.random()*3;
                sparks.add(new Spark(x, y, Math.cos(angle)*speed, Math.sin(angle)*speed));
            }

            int smokeCount = 10 + (int)(Math.random()*5);
            for(int i=0;i<smokeCount;i++){
                smokes.add(new Smoke(x, y));
            }
        }

        void update() {
            if(frame < DURATION){
                frame++;
                fireParticles.forEach(FireParticle::update);
                sparks.forEach(Spark::update);
                smokes.forEach(Smoke::update);
            }
        }

        void render(GraphicsContext gc){
            fireParticles.forEach(p -> p.render(gc));
            sparks.forEach(p -> p.render(gc));
            smokes.forEach(p -> p.render(gc));
        }

        boolean isFinished(){
            return frame >= DURATION;
        }
    }

    private final List<Explosion> explosions = new ArrayList<>();

    public ExplosionCanvas(double width, double height){
        super(width, height);
        setMouseTransparent(true);
        setStyle("-fx-background-color: transparent;");

        new AnimationTimer(){
            @Override
            public void handle(long now){
                update();
                draw();
            }
        }.start();
    }

    // Thêm vụ nổ tại tâm
    public void addExplosion(double x, double y){
        explosions.add(new Explosion(x, y));
    }

    // Thêm vụ nổ theo brick
    public void addExplosion(double x, double y, double width, double height){
        double centerX = x + width/2;
        double centerY = y + height/2;
        addExplosion(centerX, centerY);
    }

    private void update(){
        explosions.forEach(Explosion::update);
        explosions.removeIf(Explosion::isFinished);
    }

    private void draw(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        explosions.forEach(e -> e.render(gc));
    }
}
