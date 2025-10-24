package Game.Object;

import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {
    public static double startX = GameManager.WIDTH/2 - 50;
    public static double startY = GameManager.HEIGHT - 50;
    public static double WIDTH = 100;
    public static double HEIGHT = 20;
    private int speed;
    private String currentPowerUp;
    public static Image img = new Image(Brick.class.getResourceAsStream("/image/paddle.png"));
    private PowerUp.Type currentPower;
    private long powerStartTime;
    private long powerDuration = 5000; // 5 giây
 private double oldWidth;
public double newWidth ;
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
        this.oldWidth = width;
        this.newWidth = width * 1.5;
    }

    public void setCurrentPower(PowerUp.Type type) {
        this.currentPower = type;
        this.powerStartTime = System.currentTimeMillis();
        this.oldWidth = getWidth();
        if (type == PowerUp.Type.EXPAND_PADDLE) {
            setWidth(newWidth);
        }
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
        else if(getX()+getWidth()>GameManager.WIDTH) {
            setX(GameManager.WIDTH-getWidth());
        }
        else setX(getX() + getDirectionX());
    }

    /**
     * Paddle move Left,move Right update Paddle(newX,newY)
     *
     */
    public void moveLeft() {
        setDirectionX(-speed);
        move();
        setDirectionX(0);

    }

    public void moveRight() {
        setDirectionX(speed);
        move();
        setDirectionX(0);
    }

    private void resetPower() {
        this.currentPower = null;
      setWidth(oldWidth);
    }


    public void applyPowerUp(String newPowerUp) {
        this.currentPowerUp = newPowerUp;
    }

    @Override
    public void update() {
        move();
        if (currentPower != null &&
                System.currentTimeMillis() - powerStartTime > powerDuration) {
            resetPower();
        }
    }

    @Override
    public  void render(GraphicsContext gc) {
        if(img != null) {
            gc.drawImage(img,getX(),getY(),getWidth(),getHeight());
        }
        else {
            gc.setFill(Color.BLUE);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void reset() {
        setX(Paddle.startX);
        setY(Paddle.startY);
        setWidth(Paddle.WIDTH);
        this.oldWidth = Paddle.WIDTH;
        setHeight(Paddle.HEIGHT);

    }
    public static void main(String[] args) {

    }
}
