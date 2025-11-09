package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.util.Config;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;

public class ExtraBallPowerUp extends PowerUp {

    public ExtraBallPowerUp(double x, double y, Image spriteSheet) {
        super(x, y, 30, 30,
                spriteSheet,
                spriteSheet != null ? 4 : 1,
                1,
                spriteSheet != null ? 120 : 0,
                spriteSheet != null ? 30 : 0,
                0.15,
                2, 2, 26, 26,
                Color.BLUE);
    }

    // Constructor không sprite
    public ExtraBallPowerUp(double x, double y) {
        this(x, y, null);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        // FIX: Lấy ball BẤT KỲ còn trên sân (không nhất thiết là ball chính)
        List<Ball> currentBalls = context.getBalls();
        if (currentBalls.isEmpty()) return; // Không có ball nào thì không spawn

        // Lấy ball đầu tiên trong list (bất kỳ ball nào còn sống)
        Ball sourceBall = currentBalls.get(0);

        try {
            Image ballSprite = new Image(
                    getClass().getResource("/com/example/arkanoidProject/view/images/ball/ballSpriteImage.png").toExternalForm()
            );

            // Tạo 2 ball mới từ VỊ TRÍ của ball hiện có
            for (int i = 0; i < 2; i++) {
                Ball newBall = new Ball(
                        sourceBall.getX(), sourceBall.getY(),
                        Config.ballWidth, Config.ballHeight,
                        ballSprite, 10, 1, 880, 512, 0.1,
                        Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY,
                        Config.ballHitBoxW, Config.ballHitBoxH
                );

                // Góc -30° và +30°
                double angle = Math.toRadians(i == 0 ? -30 : 30);
                double speed = Math.sqrt(sourceBall.getDx() * sourceBall.getDx() +
                        sourceBall.getDy() * sourceBall.getDy());

                newBall.setDx(speed * Math.sin(angle));
                newBall.setDy(-speed * Math.cos(angle));
                newBall.stopHolding();

                currentBalls.add(newBall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Extra Ball";
    }

    @Override
    public String getSymbol() {
        return "B"; // B for Ball
    }
}