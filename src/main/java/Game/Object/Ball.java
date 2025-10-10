package Game.Object;

import Game.AbstractObject.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;

public class Ball extends MovableObject {

    public Ball(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }
}
