package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExpandPaddlePowerUp extends PowerUp{
    public ExpandPaddlePowerUp(double x, double y, double width, double height, int duration) {
        super(x, y, width, height, duration);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        double oldWidth = paddle.getWidth();
        double newPaddleWidth = oldWidth * 1.3;

        double centerX = paddle.getX() + oldWidth / 2.0;
        double newX = centerX - newPaddleWidth / 2.0;
        paddle.setX(Math.max(0, Math.min(newX, Config.getScreenWidth() - newPaddleWidth)));

        paddle.setWidth(newPaddleWidth);


        paddle.setHitBox(new Rectangle2D(paddle.getX() + paddle.getHitBoxOffsetX(),
                paddle.getY() + paddle.getHitBoxOffsetY(),
                newPaddleWidth, paddle.getHeight()));

    }

    @Override
    public void removeEffect(Paddle paddle) {

        double oldCenterX = paddle.getX() + paddle.getWidth() / 2.0;
        paddle.setWidth(Config.paddleWidth);
        paddle.setHeight(Config.paddleHeight);
        double newX = oldCenterX - Config.paddleWidth / 2.0;
        paddle.setX(Math.max(0, Math.min(newX, Config.getScreenWidth() - Config.paddleWidth)));

        paddle.setHitBox(new Rectangle2D(paddle.getX() + Config.paddleHitBoxOffsetX,
                paddle.getY() + Config.paddleHitBoxOffsetY,
                Config.paddleHitBoxW, Config.paddleHitBoxH));
    }


    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x,y,width,height);
    }
}
