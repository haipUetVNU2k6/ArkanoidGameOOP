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
