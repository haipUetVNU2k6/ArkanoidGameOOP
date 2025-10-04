package Game.Object;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

    /*
     ** Constructor
     * @param x,y,width,height
     */
    public GameObject(int x,int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    /*
     ** Get and set
     */

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);

}
