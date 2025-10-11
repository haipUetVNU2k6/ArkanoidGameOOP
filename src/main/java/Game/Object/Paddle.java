package Game.Object;

import Game.AbstractObject.MovableObject;
import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {
    private int speed;
    private String currentPowerUp;

    /**
     * Constructor Paddle
     *
     * @param x  Coordinate-x
     * @param y   Coordinate-y
     * @param width  Paddle's width
     * @param height  Paddle's height
     * @param speed   Paddle's speed
     */
    public Paddle(double x,double y,double width,double height,int speed) {
        super(x,y,width,height,0,0);
        this.speed = speed;
        this.currentPowerUp = null;
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getCurrentPowerUp() {
        return this.currentPowerUp;
    }
    public void setCurrentPowerUp(String current) {
        this.currentPowerUp = current;
    }

    /**
     * update Paddle if x < 0 -> set x= 0 or if x> Width(screen)  -> x = width
     * otherwise x = x + dx
     */
    @Override
    public void move() {
        if(getX() < 0 ) {
            setX(0);
        }
        else if(getX()>GameManager.WIDTH) {
            setX(GameManager.WIDTH);
        }
        else setX(getX() + dx);
    }

    /**
     * Paddle move Left,move Right update Paddle(newX,newY)
     *
     */
    public void moveLeft() {
        dx = -speed;
        move();
        dx = 0;

    }

    public void moveRight() {
        dx = speed;
        move();
        dx = 0 ;
    }

    public void applyPowerUp(String newPowerUp) {
        this.currentPowerUp = newPowerUp;
    }
    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    public static void main(String[] args) {

    }
}
