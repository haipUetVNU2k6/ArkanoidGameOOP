package Game.Object;

import Game.AbstractObject.GameObject;
import Game.AbstractObject.MovableObject;
import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Ball extends  MovableObject{
    private int speed;
    private double directionX;
    private double directionY;
    public enum Direction{
        top,down,
        left,right;
    }
    public Ball(double x,double y,double radius,int speed,double directionX,double directionY) {
        super(x,y,2*radius,2*radius,directionX*speed,directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public double getDirectionX() {
        return this.directionX;
    }
    public void setDirectionX(double x) {
        this.directionX = x;
    }

    public double getDirectionY() {
        return this.directionY;
    }
    public void setDirectionY(double y) {
        this.directionY = y;
    }

    public Direction intersect(GameObject obj) {
        double overlapTop = obj.getY() + obj.getHeight() - (this.getY() - this.getHeight());
        double overlapDown = obj.getY() - (this.getY()+this.getHeight());
        double overlapLeft = obj.getX() - (this.getX()+this.getWidth());
        double overlapRight = obj.getX() + obj.getWidth() - (this.getX() - this.getWidth());
    }

    public void bounceOff(Direction dir) {

    }
    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getHeight(), getWidth());
    }
    public static void main(String[] args) {

    }
}
