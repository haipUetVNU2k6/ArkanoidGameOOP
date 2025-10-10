package com.example.arkanoidgameoop.model;

public class Brick extends GameObject {
    public boolean alive = true;
    public Brick(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
