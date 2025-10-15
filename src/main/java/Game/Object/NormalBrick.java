package Game.Object;

import javafx.scene.image.Image;

public class NormalBrick extends Brick {
    public NormalBrick(double x,double y,double width,double height) {
        super(x,y,width,height,1);
        img = new Image(NormalBrick.class.getResourceAsStream("/image/normalBrick.png"));
    }
}       

