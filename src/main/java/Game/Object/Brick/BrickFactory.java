package Game.Object.Brick;

public class BrickFactory {
    public static Brick createBrick(int id ,double x,double y,double width,double height) {
        switch (id) {
            case 1:
                return new NormalBrick(x,y,width,height);
            case 2:
                return new  TNT(x,y,width,height);
            case 3:
                return new StrongBrick(x,y,width,height);
            default:
                return null;

        }


    }
    public static void main(String[] args) {

    }
}
