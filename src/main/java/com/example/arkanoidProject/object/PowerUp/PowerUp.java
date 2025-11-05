package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.GameObject;
import com.example.arkanoidProject.object.MoveableObject;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;
import javafx.geometry.Rectangle2D;

public abstract class PowerUp extends MoveableObject {
    private int duration;

    /**
     * Constructor.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     * @param width chiều rộng
     * @param height chiều dài
     * @param duration khoảng thời gian
     */
    public PowerUp(double x, double y, double width, double height,int duration) {
        // dy = ?
        super(x, y, width, height,0, 5);
        this.duration = duration;
    }

    public abstract void applyEffect(Paddle paddle);

    public abstract void removeEffect(Paddle paddle);

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void reset() {}

    @Override
    public void update(double dt) {
       // if(getY() > Config.getScreenHeight())
        setY(getY() + getDy());
        hitBox = new Rectangle2D(
                getX() ,
                getY() ,
                getWidth(),
                getHeight()
        );
    }
}
