package com.example.arkanoidgameoop.model;

public abstract class MovableObject extends GameObject {
    protected double vx, vy;
    public void setVx(double v){vx = v;}
    public void setVy(double v){vy = v;}
    public double getVx(){return vx;}
    public double getVy(){return vy;}
    public void updatePosition(double dt){
        x += vx * dt;
        y += vy * dt;
    }
}


//public abstract class MovableObject extends GameObject {
//    protected double dx, dy;
//
//    public MovableObject(double x, double y, int width, int height, double dx, double dy) {
//        super(x, y, width, height);
//        this.dx = dx;
//        this.dy = dy;
//    }
//
//    public void move() {
//        x += dx;
//        y += dy;
//    }
//
//    public abstract void update();
//
//    // Getters and setters for velocity
//    public double getDx() { return dx; }
//    public double getDy() { return dy; }
//    public void setDx(double dx) { this.dx = dx; }
//    public void setDy(double dy) { this.dy = dy; }
//}