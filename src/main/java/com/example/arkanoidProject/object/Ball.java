package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

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

        this.velocityX = 0;
        this.velocityY = 0;
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
            // Tính toán góc xoay từ vận tốc của quả bóng
            double angle = Math.atan2(velocityY, velocityX) ; // Góc tính theo radian
            if(velocityX == 0 && velocityY == 0) {
                angle = Math.PI/2;
            }

            // Tính tỷ lệ phóng đại của sprite để tránh méo hình
            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY); // Tránh méo hình

            // Lưu trạng thái hiện tại của GraphicsContext
            gc.save();

            // Di chuyển gốc tọa độ đến trung tâm quả bóng (để xoay quanh tâm)
            gc.translate(x + width / 2, y + height / 2);

            // Xoay GraphicsContext theo góc tính được
            gc.rotate(Math.toDegrees(angle)); // Chuyển góc từ radian sang độ

            // Vẽ quả bóng sau khi xoay, điều chỉnh vị trí để vẽ từ tâm
            spriteAnimation.render(gc, -width / 2, -height / 2, scale, scale); // Vẽ từ trung tâm

            // Khôi phục lại trạng thái GraphicsContext
            gc.restore();
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
