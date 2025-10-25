package Game.Object.PowerUp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import Game.Object.*;

public class PowerUp extends MovableObject {

    public enum Type {
        EXPAND_PADDLE
    }

    private Type type;
    private boolean active = true;
    public PowerUp(double x, double y, double width, double height, Type type) {
        super(x, y, width, height, 0, 2);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    public boolean isActive() {
        return active;
    }
    public void deactivate() {
        active = false;
    }

    public boolean collidesWith(Paddle paddle) {
        return getX() < paddle.getX() + paddle.getWidth() &&
                getX() + getWidth() > paddle.getX() &&
                getY() < paddle.getY() + paddle.getHeight() &&
                getY() + getHeight() > paddle.getY();
    }
    @Override
    public void move() {
        setX(getX() + getDirectionX());
        setY(getY() + getDirectionY());
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;

        gc.setFill(Color.LIGHTGREEN);
        gc.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void reset() {
        type = null;
    }

}


