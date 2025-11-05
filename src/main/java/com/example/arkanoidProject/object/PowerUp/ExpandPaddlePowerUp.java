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
        double newPaddleWidth   = paddle.getHitBox().getWidth() * 0.9;
        double newPaddleOffSetX = paddle.getHitBoxOffsetX() * 0.9;
        double paddleHeight   = paddle.getHitBox().getHeight();
        double paddleOffSetY = paddle.getHitBoxOffsetY() ;
        paddle.setWidth(newPaddleWidth);
        paddle.setHitBox(new Rectangle2D(x + newPaddleOffSetX,
                y + paddleOffSetY,newPaddleWidth, paddleHeight ));
    }

    @Override
    public void removeEffect(Paddle paddle) {
        setWidth(Config.paddleWidth);
        setHeight(Config.paddleHeight);
    }

    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x,y,width,height);
    }
}
