package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Paddle extends MovableObject{
    private int speed;
    private String currentPowerUp;

    public Paddle(int x,int y,int width,int height,int speed,String currentPowerUp) {
        super(x,y,width,height,0,0);
        this.speed = speed;
        this.currentPowerUp = currentPowerUp;
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
