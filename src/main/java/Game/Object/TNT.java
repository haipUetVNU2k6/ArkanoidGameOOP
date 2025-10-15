package Game.Object;

import javafx.scene.image.Image;

public class TNT extends Brick {
    public TNT(double x,double y,double width,double height) {
        super(x, y, width, height, 2);
        img = new Image(NormalBrick.class.getResourceAsStream("/image/tnt.png"));
    }
}
