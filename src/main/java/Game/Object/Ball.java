package Game.Object;

import Game.AbstractObject.GameObject;
import Game.AbstractObject.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Ball extends  MovableObject{
    private  int speed;
    private int directionX;
    private int directionY;

    public Ball(int x,int y,int radius,int speed,int directionX,int directionY) {
        super(x,y,2*radius,2*radius,directionX*speed,directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getDirectionX() {
        return this.directionX;
    }
    public void setDirectionX(int x) {
        this.directionX = x;
    }

    public int getDirectionY() {
        return this.directionY;
    }
    public void setDirectionY(int y) {
        this.directionY = y;
    }
    public void bounceOff(GameObject brick) {
       this.directionX = -directionX;
       dx = speed*directionX;
    }

    public boolean  checkCollision(GameObject other ) {
        return true;
    }
    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getHeight(), getWidth());
    }
    public static void main(String[] args) {

    }
}
