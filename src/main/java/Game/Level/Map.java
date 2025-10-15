package Game.Level;

import Game.Object.Brick;

import java.util.ArrayList;

public  class Map {
    private ArrayList<Brick> map;
    private int ID;
    public Map(int id) {
        map = new ArrayList<>();
        ID = id;
    }

    public ArrayList<Brick> getMap() {
        return map;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }
    public static void main(String[] args) {

    }
}
