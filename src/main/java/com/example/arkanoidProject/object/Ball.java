package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Ball extends MoveableObject {
    private boolean isHeld = true;

    public Ball(double x, double y, double width, double height,
                Image spriteSheet, int columns, int rows,
                int frameWidth, int frameHeight, double frameDuration,
                double hitBoxOffsetX, double hitBoxOffsetY, double hitBoxW, double hitBoxH) {

        super(x, y, width, height,
                new SpriteAnimation(
                        spriteSheet,
                        frameWidth,
                        frameHeight,
                        columns,
                        rows,
                        frameDuration),
                hitBoxOffsetX, hitBoxOffsetY, hitBoxW, hitBoxH);

        this.dx = Config.startBallDx;
        this.dy = Config.startBallDy;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            // 🔹 Tính góc xoay dựa vào hướng di chuyển
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY);

            gc.save();

            // 🔹 Tâm xoay = tâm phần bóng thật (bỏ qua shadow)
            double centerX = x + hitBoxOffsetX + hitBox.getWidth() / 2;
            double centerY = y + hitBoxOffsetY + hitBox.getHeight() / 2;

            // Di chuyển gốc tọa độ đến tâm bóng
            gc.translate(centerX, centerY);

            // Xoay sprite theo hướng bay
            gc.rotate(angle);

            // 🔹 Dịch sprite về vị trí đúng (do translate trước đó)
            gc.translate(-hitBoxOffsetX - hitBox.getWidth() / 2,
                    -hitBoxOffsetY - hitBox.getHeight() / 2);

            // 🔹 Vẽ quả bóng (sprite sheet frame hiện tại)
            spriteAnimation.render(gc, 0, 0, scale, scale);



            gc.restore();
        }
    }

    public boolean isHeld() {
        return isHeld;
    }

    public void stopHolding() {
        isHeld = false;
    }
}
