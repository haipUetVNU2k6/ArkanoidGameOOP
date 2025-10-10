package com.example.arkanoidgameoop.model;

public class Ball extends MovableObject {
    public double radius;

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.width = radius*2;
        this.height = radius*2;
        // initial velocity
        this.vx = 200;
        this.vy = -200;
    }

    public void reset(double cx, double cy) {
        this.x = cx - radius;
        this.y = cy - radius;
        this.vx = 200;
        this.vy = -200;
    }
}
