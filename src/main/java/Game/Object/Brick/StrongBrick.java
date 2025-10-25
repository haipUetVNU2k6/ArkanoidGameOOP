package Game.Object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StrongBrick extends Brick {
    public static final int id=3;
    public static final Image img  = new Image(NormalBrick.class.getResourceAsStream("/image/obsidianBrick.png"));
    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height,2,id);

    }
    @Override
    public void render(GraphicsContext gc) {
        if(img != null) {
           if(getHitPoints() == 2) gc.drawImage(img,getX(),getY(),getWidth(),getHeight());
           else gc.drawImage(NormalBrick.img,getX(),getY(),getWidth(),getHeight());
        }
        else {
            gc.setFill(Color.GREEN);
            gc.fillRect(getX(),getY(),getWidth(),getHeight());
        }
    }

}
