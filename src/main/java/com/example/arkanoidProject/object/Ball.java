package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MoveableObject {
    public static boolean showHitbox = false; // ✅ cho phép bật/tắt hitbox

    private double screenWidth, screenHeight; // Giới hạn màn hình để xử lý va chạm

    public Ball(double x, double y, double width, double height, Image spriteSheet, int columns, int rows,
                int frameWidth, int frameHeight, double frameDuration,
                double screenWidth, double screenHeight) {

        super(x, y, width, height,
                new SpriteAnimation(
                        spriteSheet,
                        frameWidth,
                        frameHeight,
                        columns,
                        rows,
                        frameDuration));

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.velocityX = 200;
        this.velocityY = -200;
    }



    @Override
    public void update(double dt) {
        super.update(dt);

        // Va chạm biên trái/phải màn hình
        if (x <= 0) {
            x = 0;
            velocityX = -velocityX;
        }
        if (x + width >= screenWidth) {
            x = screenWidth - width;
            velocityX = -velocityX;
        }

        // Va chạm biên trên
        if (y <= 0) {
            y = 0;
            velocityY = -velocityY;
        }

        // Va chạm biên dưới (thua game hoặc mất life, ở đây chỉ bounce lại tạm)
        if (y + height >= screenHeight) {
            y = screenHeight - height;
            velocityY = -velocityY;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            double angle = Math.atan2(velocityY, velocityX);

            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY);

            gc.save();

            // ✅ Dịch tâm xoay sang phải 5px
            double offsetX = 0;
            double offsetY = 0;
            gc.translate(x + width / 2 + offsetX, y + height / 2 + offsetY);

            gc.rotate(Math.toDegrees(angle));

            spriteAnimation.render(gc, -width / 2, -height / 2, scale, scale);

            gc.restore();
        }

        // ✅ Vẽ hitbox nếu được bật
        if (showHitbox) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(1.5);
            gc.strokeRect(x, y, width, height);
        }
    }


    public void resetPosition(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = -Math.abs(this.velocityY); // hoặc giá trị ban đầu
    }


    // Hàm va chạm với paddle hoặc brick có thể thêm ở đây (nếu muốn)
}
