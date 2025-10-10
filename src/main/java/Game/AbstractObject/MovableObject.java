package Game.AbstractObject;

import javafx.scene.canvas.GraphicsContext;

public abstract class MovableObject extends GameObject {
    protected double dx;
    protected double dy;

    /*
     **Constructor
     * @param dx,dy
     */
    public MovableObject(double x,double y,double width,double height,double dx,double dy) {
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }

    /*
     ** Move()
     */
    public void move() {
        double x = getX();
        double y = getY();
        setX(x+dx);
        setY(y+dy);
    }


    @Override
    public void update() {
        move();
    }

    @Override
    public abstract void render(GraphicsContext gc);
    public static void main(String[] args) {

    }
}
