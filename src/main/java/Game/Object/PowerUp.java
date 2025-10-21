package Game.Object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class PowerUp extends MovableObject {

    public enum Type {
        EXPAND_PADDLE,   // mo rong thanh do
        MULTI_BALL,      // them bong
        EXTRA_LIFE       // them mang
    }

    private Type type;

    public PowerUp(double x, double y, double width, double height, Type type) {
        super(x, y, width, height, 0, 2);
        this.type = type;
    }

    public Type getType() {
        return type;
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
        switch (type) {
            case EXPAND_PADDLE -> gc.setFill(Color.BLUE);
            case MULTI_BALL -> gc.setFill(Color.YELLOW);
            case EXTRA_LIFE -> gc.setFill(Color.GREEN);
        }
        gc.fillOval(getX(), getY(), getWidth(), getHeight());
    }
    public void applyEffect (Paddle paddle) {
        switch (type) {
            case EXPAND_PADDLE :
            {
                paddle.setWidth(paddle.getWidth() * 1.5);
            }
            case MULTI_BALL :
            { }
            case EXTRA_LIFE :
            { }
        }
    }
    public void reset() {

    }
}


