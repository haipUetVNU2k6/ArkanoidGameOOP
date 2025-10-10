package com.example.arkanoidgameoop.model;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveTo(double newX) {
        this.x = newX;
    }
}
