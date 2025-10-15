package Game.Level;

import Game.Manage.GameManager;
import Game.Object.Brick;

import java.util.ArrayList;

public class MapOne extends Map{
    private static int id = 1;
    public MapOne() {
        super(1);
        for(int i=0;i<8;++i) {
            for(int j=0;j<10;++j) {
                Brick newBrick = new Brick(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30,1,1);
                this.getMap().add(newBrick);
            }

        }
    }
    public static void main(String[] args) {

    }
}
