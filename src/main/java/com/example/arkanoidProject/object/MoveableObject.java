package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;

public class MoveableObject extends GameObject {
    protected double velocityX, velocityY;

    public MoveableObject(double x, double y, double width, double height, SpriteAnimation spriteAnimation) {
        super(x, y, width, height, spriteAnimation);
        this.velocityX = 0;
        this.velocityY = 0;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        x += velocityX * dt;
        y += velocityY * dt;
    }


    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
}
