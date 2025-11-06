package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShrinkPaddlePowerUp extends PowerUp{
    public ShrinkPaddlePowerUp(double x, double y, double width, double height, int duration) {
        super(x, y, width, height, duration);
    }

    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(x,y,width,height);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        double newPaddleWidth  = paddle.getWidth() * 0.7;
        paddle.setWidth(newPaddleWidth);
        paddle.setHitBox(new Rectangle2D(paddle.getX() ,
                paddle.getY(),newPaddleWidth, paddle.getHeight() ));
    }

    @Override
    public void removeEffect(Paddle paddle) {
        System.out.println("in here.");
        paddle.setWidth(Config.paddleWidth);
        paddle.setHeight(Config.paddleHeight);
        paddle.setHitBox(new Rectangle2D(paddle.getX() + Config.paddleHitBoxOffsetX,
                paddle.getY() + Config.paddleHitBoxOffsetY,
                Config.paddleHitBoxW, Config.paddleHitBoxH));
    }
}
