package Game.Object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ObsidianBrick extends Brick {
    public static final int id=2;
    public static final Image img  = new Image(NormalBrick.class.getResourceAsStream("/image/obsidianBrick.png"));
    public ObsidianBrick(double x,double y,double width,double height) {
        super(x, y, width, height,3);

    }
    @Override
    public void render(GraphicsContext gc) {
        if(img != null) {
            gc.drawImage(img,getX(),getY(),getWidth(),getHeight());
        }
        else {
            gc.setFill(Color.GREEN);
            gc.fillRect(getX(),getY(),getWidth(),getHeight());
        }
    }

}
