package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.BallManager;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MagnetPowerUp extends PowerUp{
    public MagnetPowerUp(double x, double y, double width, double height, int duration) {
        super(x, y, width, height, duration);
    }

    @Override
    public void applyEffect(Paddle paddle, BallManager ballManager) {
              paddle.setHoldingBall(true);
    }

    @Override
    public void removeEffect(Paddle paddle, BallManager ballManager) {
            paddle.setHoldingBall(false);

            for (Ball ball : ballManager.getBalls()) {
                if (ball.isHeld()) {
                    ball.stopHolding();

                    double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
                    double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
                    ball.setDx(0);
                    ball.setDy(-Math.abs(Config.startBallDy));
                }
            }
        }

    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.YELLOWGREEN);
        gc.fillOval(x,y,width,height);
    }
}
