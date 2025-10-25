package Game.Object;

import Game.Manage.GameManager;
import Game.Object.Brick.Brick;
import Game.Object.PowerUp.PowerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {
    public static double startX = GameManager.WIDTH/2 - 50;
    public static double startY = GameManager.HEIGHT - 50;
    public static double WIDTH = 100;
    public static double HEIGHT = 20;
    private int speed;
    public static Image img = new Image(Brick.class.getResourceAsStream("/image/paddle.png"));
    private PowerUp.Type currentPowerUp;
    private long powerStartTime;
    private long powerDuration = 5000; // 5 seconds

    public double getOldX() {
        return oldX;
    }

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    private  double oldX;
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
        this.oldX = x;
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public long getPowerDuration() {
        return powerDuration;
    }

    public void setPowerDuration(long powerDuration) {
        this.powerDuration = powerDuration;
    }

    public long getPowerStartTime() {
        return powerStartTime;
    }

    public void setPowerStartTime(long powerStartTime) {
        this.powerStartTime = powerStartTime;
    }

    public PowerUp.Type getCurrentPowerUp() {
        return this.currentPowerUp;
    }
    public void setCurrentPowerUp(PowerUp.Type type) {
        this.currentPowerUp = type;
        this.powerStartTime = System.currentTimeMillis();

        switch (type) {
            case EXPAND_PADDLE:
                setWidth(getWidth() * 1.5);
                break;

            case EXTRA_LIFE:
                GameManager.lives++;
                break;
            case MULTI_BALL:
                // Gọi qua GameManager để sinh thêm bóng
                GameManager.getInstance().spawnExtraBalls();
                break;

            case SHOOTING_PADDLE:
                // Bật chế độ bắn đạn trong GameManager
                GameManager.getInstance().enableShootingMode(true);
                break;
        }

    }

    /**
     * update Paddle if x < 0 -> set x= 0 or if x> Width(screen)  -> x = width
     * otherwise x = x + dx
     */
    @Override
    public void move() {
        setOldX(getX());
        double newX = getX() + getDirectionX();

        // check
        if (newX < 0) {
            setX(0); // Chặn ở 0
        } else if (newX + getWidth() > GameManager.WIDTH) {
            setX(GameManager.WIDTH - getWidth());
        } else {
            setX(newX);
        }
    }

    /**
     * Paddle move Left,move Right update Paddle(newX,newY)
     *
     */
    public void moveLeft() {
        setDirectionX(-speed);
    }

    public void moveRight() {
        setDirectionX(speed);
    }

    public void stopMoving() {
        setDirectionX(0);
    }



    // fix ???
public void resetPower() {
        switch (currentPowerUp) {
            case EXPAND_PADDLE:
                setWidth(Paddle.WIDTH);
                break;
            case SHOOTING_PADDLE:
                GameManager.getInstance().enableShootingMode(false);
                break;
        }
        currentPowerUp = null;
    }


//    public void applyPowerUp(PowerUp.type newPowerUp) {
//        this.currentPowerUp = newPowerUp;
//    }

    @Override
    public void update() {
        move();
        //System.out.println(currentPowerUp);
        if (currentPowerUp != null
                && System.currentTimeMillis() - powerStartTime > powerDuration) {
            System.out.println("in here.");
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
        setHeight(Paddle.HEIGHT);

    }
    public static void main(String[] args) {

    }
}
