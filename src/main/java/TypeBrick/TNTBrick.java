package TypeBrick;


import Game.Object.Brick;
import java.util.List;

public class TNTBrick extends Brick {
    private List<Brick> bricks;

    public TNTBrick(int x, int y, int height, int width, List<Brick> bricks) {
        super(x, y, height, width, 1, 3);
        this.bricks = bricks;
    }

    @Override
    public void takeHit(int amount) {
        super.takeHit(amount);

        if (isDestroyed()) {
            explode();
        }
    }

    private void explode() {
        double brickWidth = getWidth();
        double brickHeight = getHeight();

        for (Brick b : bricks) {
            boolean isNeighbor =
                    (b.getX() == getX() - brickWidth && b.getY() == getY()) || // trái
                            (b.getX() == getX() + brickWidth && b.getY() == getY()) || // phải
                            (b.getY() == getY() - brickHeight && b.getX() == getX()) || // trên
                            (b.getY() == getY() + brickHeight && b.getX() == getX());   // dưới

            if (isNeighbor && !b.isDestroyed()) {
                b.takeHit(3);
            }
        }
    }
}
