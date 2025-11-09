package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.BallManager;
import com.example.arkanoidProject.object.MoveableObject;
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
    public void applyEffect(Paddle paddle, BallManager ballManager) {


            double newVisualWidth  = paddle.getWidth() * 0.5;
            paddle.setWidth(newVisualWidth);
            double newHitBoxW  = paddle.getHitBox().getWidth() * 0.5;
            paddle.setHitBox(new Rectangle2D(paddle.getX() +  Config.paddleHitBoxOffsetX,
                    paddle.getY() + Config.paddleHitBoxOffsetY,newHitBoxW, paddle.getHitBox().getHeight() ));


    }

    @Override
    public void removeEffect(Paddle paddle, BallManager ballManager) {
            paddle.setWidth(Config.paddleWidth);
            paddle.setHeight(Config.paddleHeight);
            paddle.setHitBox(new Rectangle2D(paddle.getX() + Config.paddleHitBoxOffsetX,
                    paddle.getY() + Config.paddleHitBoxOffsetY,
                    Config.paddleHitBoxW, Config.paddleHitBoxH));

    }
}
