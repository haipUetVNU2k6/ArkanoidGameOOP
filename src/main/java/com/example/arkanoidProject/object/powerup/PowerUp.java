package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.object.MoveableObject;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class PowerUp extends MoveableObject {
    protected boolean collected = false;
    protected Color fallbackColor;

    public PowerUp(double x, double y, double width, double height,
                   Image spriteSheet, int frameCount, int frameRows,
                   int sheetWidth, int sheetHeight, double frameDuration,
                   double hitBoxOffsetX, double hitBoxOffsetY,
                   double hitBoxW, double hitBoxH,
                   Color fallbackColor) {

        super(x, y, width, height,
                spriteSheet != null ? new SpriteAnimation(spriteSheet, frameCount, frameRows, sheetWidth, sheetHeight, frameDuration) : null,
                hitBoxOffsetX, hitBoxOffsetY, hitBoxW, hitBoxH);

        this.dy = 100; // tốc độ rơi xuống
        this.fallbackColor = fallbackColor;
    }

    // Constructor đơn giản (không dùng sprite)
    public PowerUp(double x, double y, Color fallbackColor) {
        this(x, y, 30, 30, null, 1, 1, 0, 0, 0, 0, 0, 30, 30, fallbackColor);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            super.render(gc); // dùng render của MoveableObject
        } else {
            // Vẽ hình vuông màu
            gc.setFill(fallbackColor);
            gc.fillRect(x, y, width, height);

            // Viền trắng
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, width, height);

            // Ký hiệu đại diện
            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font(16));
            String symbol = getSymbol();
            gc.fillText(symbol, x + width/2 - 5, y + height/2 + 5);
        }
    }

    // Abstract methods - mỗi PowerUp tự implement
    public abstract void applyEffect(PowerUpContext context);
    public abstract String getName();
    public abstract String getSymbol();

    // Getters/Setters
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }
}