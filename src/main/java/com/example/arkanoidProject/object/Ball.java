package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class Ball extends MoveableObject {

    private double screenWidth, screenHeight; // Giới hạn màn hình để xử lý va chạm

    public Ball(double x, double y, double diameter, Image spriteSheet, int columns, int rows,
                int frameWidth, int frameHeight, double frameDuration,
                double screenWidth, double screenHeight) {

        super(x, y, diameter, diameter,
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

    public void resetPosition(int i, int i1) {
        x = i;
        y = i1;
//        velocityX = -velocityX;
//        velocityY = -velocityY;
    }

    // Hàm va chạm với paddle hoặc brick có thể thêm ở đây (nếu muốn)
}
