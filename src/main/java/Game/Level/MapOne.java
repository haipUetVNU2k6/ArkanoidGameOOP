package Game.Level;

import Game.Manage.GameManager;
import Game.Object.Brick;
import Game.Object.NormalBrick;
import Game.Object.TNT;
import Game.Object.StrongBrick;
import java.util.Random;

public class MapOne extends Map{
    private static int id = 1;
    public MapOne() {
        super(1);
        for(int i=0;i<8;++i) {
            for(int j=0;j<10;++j) {
                Random rand = new Random();
                int randomInt = 1 + rand.nextInt(3);
                Brick newBrick = null;
                switch (randomInt) {
                    case 1:
                        newBrick = new NormalBrick(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30);
                        break;
                    case 2:
                        newBrick = new TNT(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30);
                        break;
                    case 3:
                        newBrick = new StrongBrick(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30);
                        break;
                }
                this.getMap().add(newBrick);
            }

        }
    }
    public static void main(String[] args) {

    }
}
