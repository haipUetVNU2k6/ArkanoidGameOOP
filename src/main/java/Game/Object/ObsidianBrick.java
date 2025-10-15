package Game.Object;

import javafx.scene.image.Image;

public class ObsidianBrick extends Brick {
    public ObsidianBrick(double x,double y,double width,double height) {
        super(x, y, width, height, 3);
        img = new Image(NormalBrick.class.getResourceAsStream("/image/obsidianBrick.png"));
    }

    
}