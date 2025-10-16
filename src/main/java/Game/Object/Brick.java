package Game.Object;

import Game.AbstractObject.GameObject;
import Game.GameView;
import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class Brick extends GameObject {
    private int hitPoints;
    protected int id;
    /*new Image(Brick.class.getResourceAsStream("/image/brick.png"));*/

    /**
     * Constructor Brick.
     *
     * @param x       Coordinate-x.
     * @param y       Coordinate-y.
     * @param height  object's height.
     * @param width   object's width.
     * @param hitPoints  object's hit points.
     * @param type       object id: 1 la normal, 2 la TNT, 3 la obsidian.
     */
    public Brick(double x,double y,double width,double height,int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
    }


    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setHitPoints(int point) {
        this.hitPoints = point;
    }

    public void takeHit(int amount) {
        this.hitPoints = this.hitPoints - amount;
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


