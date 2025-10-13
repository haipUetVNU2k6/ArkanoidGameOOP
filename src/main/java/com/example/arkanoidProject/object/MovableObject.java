package com.example.arkanoidProject.object;

public abstract class MovableObject extends GameObject {
    protected double dx = 0;
    protected double dy = 0;

    public void updatePosition() {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
