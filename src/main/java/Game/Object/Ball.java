package Game.Object;

import Game.AbstractObject.GameObject;
import Game.AbstractObject.MovableObject;
import Game.Manage.GameManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class Ball extends  MovableObject{
    private double speed;
    public boolean isCollision = false;
    public  enum Direction{
        top,down,
        left,right,none;
    }
    public Ball(double x,double y,double radius,double speed,double directionX,double directionY) {
        super(x,y,2*radius,2*radius,directionX*speed,directionY*speed);
        this.speed = speed;
    }

    /**
     * Xac dinh huong tiep xuc ball va vat the obj
     *
     * @param rec   vat the ball va cham
     * @return Direction:Huong tiep xuc
     */
    public Direction intersect(GameObject rec) {

           // recTop : duong thang phia tren tao len HCN
           double recTop = rec.getY()+  rec.getHeight();
           double recDown = rec.getY() ;
           double recLeft = rec.getX();
           double recRight = rec.getX() + rec.getWidth();



           // ballTop: duong thang phia tren tao len ball
           double ballTop = this.getY() + this.getHeight();
           double ballDown = this.getY();
           double ballLeft = this.getX();
           double ballRight = this.getX() + this.getWidth();

           // xac dinh vung chong lan(giao nhau) -> mo sang ben phai chieu duong truc Ox -> lay Min,nguoc lai voi bien trai lay Max
           //overlapX = bien phai - bien trai = max(ballRight,recRight) - min(ballLeft,recLeft).
           double overlapX = Math.min(ballRight,recRight) -  Math.max(ballLeft,recLeft);
           double overlapY = Math.min(ballTop,recTop)  - Math.max(ballDown,recDown);

           // overlapX > overlapY -> top/down -> tâm obj < tâm ball -> top
           double pXBall = (this.getX() + this.getWidth())/2;
           double pYBall = (this.getY() + this.getHeight())/2;
           double pXRec = (rec.getX() + rec.getWidth())/2;
           double pYRec = (rec.getY() + rec.getHeight())/2;

           //System.out.println(ballLeft +","+ recRight +","+ ballRight +","+ recLeft +","+ ballTop +"," + recDown + "," +  ballDown + "," +recTop);
           if(ballLeft >= recRight || ballRight <= recLeft || ballTop <= recDown || ballDown >= recTop) {
               isCollision = false;
               return Direction.none;
           }
           else isCollision = true;

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
                   this.setDirectionY(-getDirectionY());
                   break;
               case down:
                   this.setDirectionY(-getDirectionY());
                   break;
               case right:
                   this.setDirectionX(-getDirectionX());
                   break;
               case left:
                   this.setDirectionX(-getDirectionX());
                   break;
           }

    }

    @Override
    public void move() {
        //System.out.println(getDirectionY());
        if(getX() < 0 || getX() >GameManager.WIDTH) {
            setDirectionX(-getDirectionX());
        }
        else if(getY() < 0 ) {

            setDirectionY(-getDirectionY());
        }
        else if(getY() >= GameManager.HEIGHT){

            return;
        }
        //System.out.println(getX()+","+getY()+","+getDirectionX() + ","+getDirectionY());
        setX(getX() + getDirectionX()*speed);
        setY(getY() + getDirectionY()*speed);
    }

    @Override
    public void update() {
        move();
    }
    @Override
    public  void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(getX(), getY(), getHeight(), getWidth());
    }
    public static void main(String[] args) {
        Ball ball = new Ball(GameManager.WIDTH/2-50,GameManager.HEIGHT-50,5,1.8,0,0);
        Canvas canvas = new Canvas(GameManager.WIDTH, GameManager.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        ball.render(gc);
    }
}
