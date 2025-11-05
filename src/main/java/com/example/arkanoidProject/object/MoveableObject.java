package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MoveableObject extends GameObject {
    protected double dx, dy;
    protected Rectangle2D hitBox;
    protected double hitBoxOffsetX, hitBoxOffsetY;

    protected SpriteAnimation spriteAnimation;

    public MoveableObject(double x, double y, double width, double height, SpriteAnimation spriteAnimation,
                          double hitBoxOffsetX, double hitBoxOffsetY, double hitBoxW, double hitBoxH) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;

        this.spriteAnimation = spriteAnimation;

        this.hitBoxOffsetX = hitBoxOffsetX;
        this.hitBoxOffsetY = hitBoxOffsetY;

        hitBox = new Rectangle2D(x + hitBoxOffsetX, y + hitBoxOffsetY, hitBoxW, hitBoxH);
    }

    //Có thể chưa hợp lý?
    public MoveableObject(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.hitBox = new Rectangle2D(x,y,width,height);
    }

    @Override
    public void update(double dt) {
        if (spriteAnimation != null) {
            spriteAnimation.update(dt);
        }

        x += dx * dt;
        y += dy * dt;

        // Cập nhật hitBox theo vị trí mới
        hitBox = new Rectangle2D(
                x + hitBoxOffsetX,
                y + hitBoxOffsetY,
                hitBox.getWidth(),
                hitBox.getHeight()
        );

    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY); // tránh méo hình
            spriteAnimation.render(gc, x, y, scale, scale);
        }
    }

    public void showHitBox(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(1.5);
        gc.strokeRect(
                hitBox.getMinX(),
                hitBox.getMinY(),
                hitBox.getWidth(),
                hitBox.getHeight()
        );
    }

    public Rectangle2D getHitBox() { return hitBox; }

    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }

    public void setHitBox(Rectangle2D hitBox) {
        this.hitBox = hitBox;
    }

    public double getHitBoxOffsetX() {
        return hitBoxOffsetX;
    }

    public double getHitBoxOffsetY() {
        return hitBoxOffsetY;
    }
}
