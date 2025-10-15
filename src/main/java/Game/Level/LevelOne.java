package Game.Level;

import Game.Manage.GameManager;
import Game.Object.Brick;

import java.util.ArrayList;

public class LevelOne {
    private ArrayList<Brick> Map1;

    public LevelOne() {
        Map1 = new ArrayList<>();
        for(int i=0;i<8;++i) {
            for(int j=0;j<10;++j) {
                Brick newBrick = new Brick(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30,1,1);
                Map1.add(newBrick);
            }

        }
    }
    public ArrayList<Brick> getMap1() {
        return this.Map1;
    }
    public static void main(String[] args) {

    }
}
