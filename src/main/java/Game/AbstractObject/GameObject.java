package Game.AbstractObject;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;

    /*
     ** Constructor
     * @param x,y,width,height
     */
    public GameObject(double x,double y,double width,double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    /*
     ** Get and set
     */

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);


}
