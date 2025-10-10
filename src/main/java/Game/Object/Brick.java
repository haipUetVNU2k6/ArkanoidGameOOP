package Game.Object;

import Game.AbstractObject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Brick extends GameObject {
    private int hitPoints;
    private int type;

    /**
     * Constructor Brick
     *
     * @param x       Coordinate-x
     * @param y       Coordinate-y
     * @param height  object's height
     * @param width   object's width
     * @param hitPoints  object's hit points
     * @param type       object id
     */
    public Brick(int x,int y,int height,int width,int hitPoints,int type) {
        super(x,y,height,width);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }
    public void setHitPoints(int point) {
        this.hitPoints = point;
    }

    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public void takeHit(int amout) {
        this.hitPoints = this.hitPoints - amout;
    }

    /**
     * Check brick is Destroyed
     *
     * @return if hisPoints <= 0 return true ,else return false
     */
    public boolean isDestroyed() {
        if(this.hitPoints < 1)  return true;
        else return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(getX(),getY(),getWidth(),getHeight());
    }
    @Override
    public void update() {
           if(isDestroyed()) return;
    }
    public static void main(String[] args) {

    }
}
