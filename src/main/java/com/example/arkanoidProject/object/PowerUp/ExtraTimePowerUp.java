package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.BallManager;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExtraTimePowerUp extends PowerUp{

    public ExtraTimePowerUp(double x, double y, double width, double height, int duration) {
        super(x, y, width, height, duration);
    }


    @Override
    public void applyEffect(Paddle paddle, BallManager ballManager) {

    }

    @Override
    public void removeEffect(Paddle paddle, BallManager ballManager) {

    }

    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillOval(x,y,width,height);
    }
}
