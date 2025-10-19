package Game.Object;

import javafx.scene.canvas.GraphicsContext;

public class Brick extends GameObject {
    private int hitPoints;
    private int id;
    /**
     * Constructor Brick.
     *
     * @param x       Coordinate-x.
     * @param y       Coordinate-y.
     * @param height  object's height.
     * @param width   object's width.
     * @param hitPoints  object's hit points.
     * @param id      object id: 1 la normal, 2 la TNT, 3 la obsidian.
     */
    public Brick(double x,double y,double width,double height,int hitPoints,int id) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.id = id;
    }


    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setHitPoints(int point) {
        this.hitPoints = point;
    }

    public void takeHit(int amount) {
        this.hitPoints = getHitPoints() - amount;
    }

    public int getId() {
        return id;
    }

    /**
     * Check brick is Destroyed
     *
     * @return if hisPoints <= 0 return true ,else return false
     */
    public boolean isDestroyed() {
        if(this.hitPoints <= 0) return true;
        else return false;
    }

    @Override
    public void render(GraphicsContext gc) {}
    @Override
    public void update() {
           if(isDestroyed()) return;
    }

    @Override
    public void reset() {
        return; //chua biet lam gi
    }

    public static void main(String[] args) {

    }
}


