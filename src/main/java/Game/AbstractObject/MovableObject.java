package Game.AbstractObject;

import javafx.scene.canvas.GraphicsContext;

public abstract class MovableObject extends GameObject {
    protected int dx;
    protected int dy;

    /*
     **Constructor
     * @param dx,dy
     */
    public MovableObject(int x,int y,int width,int height,int dx,int dy) {
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }

    /*
     ** Move()
     */
    public void move() {
        int x = getX();
        int y = getY();
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
