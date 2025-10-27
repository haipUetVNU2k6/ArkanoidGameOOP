package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Info;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MoveableObject {
    public static boolean showHitbox = false; // ✅ cho phép bật/tắt hitbox

    private double screenWidth, screenHeight; // Giới hạn màn hình để xử lý va chạm
    private boolean isHold;
    public Ball(double x, double y, double diameter, Image spriteSheet, int columns, int rows,

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
        this.isHold = true;

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
//        if (y + height >= screenHeight) {
//
//        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            double angle = Math.atan2(velocityY, velocityX);
            // Tính toán góc xoay từ vận tốc của quả bóng
            double angle = Math.atan2(velocityY, velocityX) ; // Góc tính theo radian
            if(velocityX == 0 && velocityY == 0) {
                angle = Math.PI/2;
            }

            // Tính tỷ lệ phóng đại của sprite để tránh méo hình
            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY);

            // Lưu trạng thái hiện tại của GraphicsContext
            gc.save();

            // ✅ Dịch tâm xoay sang phải 5px
            double offsetX = 0;
            double offsetY = 0;
            gc.translate(x + width / 2 + offsetX, y + height / 2 + offsetY);

            gc.rotate(Math.toDegrees(angle));

            spriteAnimation.render(gc, -width / 2, -height / 2, scale, scale);

            // Khôi phục lại trạng thái GraphicsContext
            gc.restore();
        }

        // ✅ Vẽ hitbox nếu được bật
        if (showHitbox) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(1.5);
            gc.strokeRect(x, y, width, height);
        }
    }

    public void bounceOf(GameObject obj,Info.Direction dir) {
        if(dir == Info.Direction.none) {
            return;
        }
       if(obj instanceof Paddle) {
           Paddle paddle = (Paddle) obj;
           if(dir != Info.Direction.top) {
               return;
           }
           else {
               double centerBallX = getX() + getWidth() / 2;
               System.out.println(centerBallX + "," + paddle.getX());
               if(centerBallX < paddle.getX() + 15) {
                   setVelocityX(- 50);
                   setVelocityY(- getVelocityY());
               }
               else if(centerBallX > paddle.getX() + paddle.getWidth() - 15) {
                   setVelocityX( 50);
                   setVelocityY(- getVelocityY());
               }
               else {
                   setVelocityX(0);
                   setVelocityY(- getVelocityY());
               }
               return;
           }
       }
        switch (dir) {
            case top:
                this.setVelocityY(-getVelocityY());
                break;
            case down:
                this.setVelocityY(-getVelocityY());
                break;
            case right:
                this.setVelocityX(-getVelocityX());
                break;
            case left:
                this.setVelocityX(-getVelocityX());
                break;
        }

    }


    @Override
    public  void reset() {
        setX(Info.BallX);
        setY(Info.BallY);
        setHold(true);
        setVelocityX(0);
        setVelocityY(0);

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean hold) {
        isHold = hold;
    }
}
