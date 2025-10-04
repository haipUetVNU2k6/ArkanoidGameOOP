package Game.Object;

import Game.AbstractObject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Brick extends GameObject {
    private int hitPoints;
    private String type;

    public Brick(int x,int y,int height,int width,int hitPoints,String type) {
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

    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void takeHit() {
        this.hitPoints = this.hitPoints - 1;
    }

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

    }
    public static void main(String[] args) {

    }
}
