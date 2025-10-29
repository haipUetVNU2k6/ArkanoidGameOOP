package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.geometry.Rectangle2D;

public class MoveableObject extends GameObject {
    protected double dx, dy;
    protected Rectangle2D hitBox;
    protected double hitBoxOffsetX, hitBoxOffsetY;

    public MoveableObject(double x, double y, double width, double height, SpriteAnimation spriteAnimation,
                          double hitBoxOffsetX, double hitBoxOffsetY, double hitBoxW, double hitBoxH) {
        super(x, y, width, height, spriteAnimation);
        this.dx = 0;
        this.dy = 0;

        this.hitBoxOffsetX = hitBoxOffsetX;
        this.hitBoxOffsetY = hitBoxOffsetY;

        hitBox = new Rectangle2D(x + hitBoxOffsetX, y + hitBoxOffsetY, hitBoxW, hitBoxH);

    }

    @Override
    public void update(double dt) {
        super.update(dt);
        x += dx * dt;
        y += dy * dt;

        // Cập nhật hitBox theo vị trí mới
        hitBox = new Rectangle2D(
                x + hitBoxOffsetX,   // cần lưu hitBoxOffsetX
                y + hitBoxOffsetY,   // cần lưu hitBoxOffsetY
                hitBox.getWidth(),
                hitBox.getHeight()
        );

    }

    public Rectangle2D getHitBox() { return hitBox; }

    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}
