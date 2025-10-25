package Game.Object;

import javafx.scene.canvas.GraphicsContext;

public abstract class MovableObject extends GameObject {
    private double directionX;
    private double directionY;

    /*
     **Constructor
     * @param dx,dy
     */
    public MovableObject(double x,double y,double width,double height,double directionX,double directionY) {
        super(x,y,width,height);
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public  double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double dx) {
        directionX = dx;
    }

    public  double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double dy) {
        directionY = dy;
    }

    /*
     ** Move()
     */
    public abstract void move();


    @Override
    public abstract void update();

    @Override
    public abstract void render(GraphicsContext gc);
    public static void main(String[] args) {

    }

}
