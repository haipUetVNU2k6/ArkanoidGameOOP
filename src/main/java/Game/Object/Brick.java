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
    public Brick(double x,double y,double width,double height,int hitPoints,int type) {
        super(x,y,width,height);
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
        InputStream i = Brick.class.getResourceAsStream("/image/block.png");
        Image img = new Image(i);
        gc.drawImage(img,getX(),getY(),getWidth(),getHeight());
    }
    @Override
    public void update() {
           if(isDestroyed()) return;
    }
    public static void main(String[] args) {

    }
}
