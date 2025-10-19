package Game.Level;

import Game.Manage.GameManager;
import Game.Object.Brick;
import Game.Object.NormalBrick;
import Game.Object.StrongBrick;
import Game.Object.TNT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Level {
    private int id;
    private String path;
    public static int maxID = 2;

    public Level(int id) {
        this.id = id;
    }
    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    private ArrayList<Brick> bricks = new ArrayList<>();

    public void loadLevel() {
        bricks.clear();
        try (Scanner read = new Scanner(Level.class.getResourceAsStream(path))) {

            int row = 0;
            while(read.hasNextLine() != false) {
                String[] Column = read.nextLine().trim().split("\\s+");
                int lenCol = Column.length;
                for(int col = 0 ;col < lenCol;++col) {
                    int id = Integer.parseInt(Column[col]);
                    Brick newBrick = null;
                    switch (id) {
                        case 1:
                            newBrick = new NormalBrick(col*(GameManager.WIDTH/10),row*(GameManager.HEIGHT/20),80,30);
                            break;
                        case 2:
                            newBrick = new TNT(col*(GameManager.WIDTH/10),row*(GameManager.HEIGHT/20),80,30);
                            break;
                        case 3:
                            newBrick = new StrongBrick(col*(GameManager.WIDTH/10),row*(GameManager.HEIGHT/20),80,30);
                            break;
                    }
                    this.bricks.add(newBrick);
                }
                row++;
            }

        }
        catch (RuntimeException e) {
            System.out.println("Error load Map.");
        }

    }
    public void nextLevel() {
        this.id ++;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static void main(String[] args) {

    }
}
