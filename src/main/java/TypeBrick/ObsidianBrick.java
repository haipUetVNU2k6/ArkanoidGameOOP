package TypeBrick;

import Game.Object.Brick;

public class ObsidianBrick extends Brick {
    public  ObsidianBrick (int x, int y, int height,int width){
        super(x,y,height,width,1,2);
    }

    @Override
    public void takeHit(int amount) {
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
