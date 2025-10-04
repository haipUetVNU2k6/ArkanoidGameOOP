package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public abstract class MovableObject extends  GameObject{
    private  int dx;
    private  int dy;

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
