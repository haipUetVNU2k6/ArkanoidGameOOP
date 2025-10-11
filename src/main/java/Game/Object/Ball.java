package Game.Object;

import Game.AbstractObject.GameObject;
import Game.AbstractObject.MovableObject;
import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Ball extends  MovableObject{
    private int speed;
    private double directionX;
    private double directionY;
    public  enum Direction{
        top,down,
        left,right,none;
    }
    public Ball(double x,double y,double radius,int speed,double directionX,double directionY) {
        super(x,y,2*radius,2*radius,directionX*speed,directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public double getDirectionX() {
        return this.directionX;
    }
    public void setDirectionX(double x) {
        this.directionX = x;
    }

    public double getDirectionY() {
        return this.directionY;
    }
    public void setDirectionY(double y) {
        this.directionY = y;
    }

    /**
     * Xac dinh huong tiep xuc ball va vat the obj
     *
     * @param rec   vat the ball va cham
     * @return Direction:Huong tiep xuc
     */
    public Direction intersect(GameObject rec) {
           // recTop : duong thang phia tren tao len HCN
           double recTop = rec.getY();
           double recDown = rec.getY() + rec.getHeight();
           double recLeft = rec.getX();
           double recRight = rec.getX() + rec.getWidth();

           // ballTop: duong thang phia tren tao len ball
           double ballTop = this.getY() + this.getHeight();
           double ballDown = this.getY();
           double ballleft = this.getX();
           double ballRight = this.getX() + this.getWidth();

           // xac dinh vung chong lan(giao nhau) -> mo sang ben phai chieu duong truc Ox -> lay Min,nguoc lai voi bien trai lay Max
           //overlapX = bien phai - bien trai = max(ballRight,recRight) - min(ballLeft,recLeft).
           double overlapX = Math.min(ballRight,recRight) -  Math.max(ballleft,recLeft);
           double overlapY = Math.min(ballTop,recTop)  - Math.max(ballDown,recDown);

           // overlapX > overlapY -> top/down -> tâm obj < tâm ball -> top
           double pXBall = (this.getX() + this.getWidth())/2;
           double pYBall = (this.getY() + this.getHeight())/2;
           double pXRec = (rec.getX() + rec.getWidth())/2;
           double pYRec = (rec.getY() + rec.getHeight())/2;

           if(overlapX == this.getWidth() && overlapY == this.getHeight() && ballTop > recTop) {
               return Direction.none;
           }
           if(overlapX >= overlapY) {
               if(pYBall >= pYRec) return Direction.top;
               else return Direction.down;
           }
           else {
               if(pXBall >= pXRec) return Direction.right;
               else return Direction.left;
           }
    }

    public void bounceOf(GameObject obj) {
        Direction dir = this.intersect(obj);
           switch (dir) {
               case top:
                   this.directionY = -this.directionY;
                   break;
               case down:
                   this.directionY = -this.directionY;
                   break;
               case right:
                   this.directionX = - this.directionX;
                   break;
               case left:
                   this.directionX = - this.directionX;
                   break;
           }
    }
    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getHeight(), getWidth());
    }
    public static void main(String[] args) {

    }
}
