package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TriplePaddlePowerUp extends PowerUp {
    private static final double DURATION = 10.0; // Hiệu ứng kéo dài 10 giây

    public TriplePaddlePowerUp(double x, double y, Image spriteSheet) {
        super(x, y, 30, 30,
                spriteSheet,
                spriteSheet != null ? 4 : 1,
                1,
                spriteSheet != null ? 120 : 0,
                spriteSheet != null ? 30 : 0,
                0.15,
                2, 2, 26, 26,
                Color.GREEN);
    }

    public TriplePaddlePowerUp(double x, double y) {
        this(x, y, null);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        context.getSidePaddles().clear();

        try {
            Image paddleSprite = new Image(
                    getClass().getResource("/com/example/arkanoidProject/view/images/paddle/paddleSpriteImage.png").toExternalForm()
            );

            Paddle mainPaddle = context.getMainPaddle();

// Paddle bên trái - hitbox sát với mainPaddle
            Paddle leftPaddle = new Paddle(
                    mainPaddle.getX() - Config.paddleHitBoxW - (Config.paddleHitBoxOffsetX * 2),
                    mainPaddle.getY(),
                    Config.paddleWidth, Config.paddleHeight,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    Config.paddleHitBoxOffsetX, Config.paddleHitBoxOffsetY,
                    Config.paddleHitBoxW, Config.paddleHitBoxH
            );

// Paddle bên phải - hitbox sát với mainPaddle
            Paddle rightPaddle = new Paddle(
                    mainPaddle.getX() + Config.paddleHitBoxW + (Config.paddleHitBoxOffsetX * 2),
                    mainPaddle.getY(),
                    Config.paddleWidth, Config.paddleHeight,
                    paddleSprite, 8, 1, 800, 640, 0.1,
                    Config.paddleHitBoxOffsetX, Config.paddleHitBoxOffsetY,
                    Config.paddleHitBoxW, Config.paddleHitBoxH
            );

            context.getSidePaddles().add(leftPaddle);
            context.getSidePaddles().add(rightPaddle);
            context.getTriplePaddleTimer()[0] = DURATION;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Triple Paddle";
    }

    @Override
    public String getSymbol() {
        return "P"; // P for Paddle
    }
}