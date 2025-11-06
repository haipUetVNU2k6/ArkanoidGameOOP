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
        System.out.println("width: " + paddle.getWidth());
        double newPaddleWidth  = paddle.getWidth() * 1.3;
        paddle.setWidth(newPaddleWidth);
        System.out.println("newWidth: " + paddle.getWidth());
        paddle.setHitBox(new Rectangle2D(paddle.getX() ,
                paddle.getY(),newPaddleWidth, paddle.getHeight() ));
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setWidth(Config.paddleWidth);
        paddle.setHeight(Config.paddleHeight);
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
