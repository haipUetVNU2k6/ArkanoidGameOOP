package Game.Object;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class Bullet extends Ball {

    public Bullet(double x, double y, double radius, double speed, double directionX, double directionY) {
        super(x, y, radius, speed, directionX, directionY);
    }

    @Override
    public void move() {
        setY(getY() + getDirectionY()*6);
    }

        @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
