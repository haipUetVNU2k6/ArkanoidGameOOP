package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Info;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Ball extends MoveableObject {

    private double screenWidth, screenHeight; // Giới hạn màn hình để xử lý va chạm
    private boolean isHold;
    public  enum Direction{
        top,down,
        left,right,none;
    }
    public Ball(double x, double y, double diameter, Image spriteSheet, int columns, int rows,
                int frameWidth, int frameHeight, double frameDuration,
                double screenWidth, double screenHeight) {

        super(x, y, diameter, diameter,
                new SpriteAnimation(
                        spriteSheet,
                        frameWidth,
                        frameHeight,
                        columns,
                        rows,
                        frameDuration));

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.isHold = true;

        this.velocityX = 0;
        this.velocityY = 0;
    }



    @Override
    public void update(double dt) {
        super.update(dt);

        // Va chạm biên trái/phải màn hình
        if (x <= 0) {
            x = 0;
            velocityX = -velocityX;
        }
        if (x + width >= screenWidth) {
            x = screenWidth - width;
            velocityX = -velocityX;
        }

        // Va chạm biên trên
        if (y <= 0) {
            y = 0;
            velocityY = -velocityY;
        }

        // Va chạm biên dưới (thua game hoặc mất life, ở đây chỉ bounce lại tạm)
//        if (y + height >= screenHeight) {
//
//        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (spriteAnimation != null) {
            // Tính toán góc xoay từ vận tốc của quả bóng
            double angle = Math.atan2(velocityY, velocityX) ; // Góc tính theo radian
            if(velocityX == 0 && velocityY == 0) {
                angle = Math.PI/2;
            }

            // Tính tỷ lệ phóng đại của sprite để tránh méo hình
            double scaleX = width / spriteAnimation.getFrameWidth();
            double scaleY = height / spriteAnimation.getFrameHeight();
            double scale = Math.min(scaleX, scaleY); // Tránh méo hình

            // Lưu trạng thái hiện tại của GraphicsContext
            gc.save();

            // Di chuyển gốc tọa độ đến trung tâm quả bóng (để xoay quanh tâm)
            gc.translate(x + width / 2, y + height / 2);

            // Xoay GraphicsContext theo góc tính được
            gc.rotate(Math.toDegrees(angle)); // Chuyển góc từ radian sang độ

            // Vẽ quả bóng sau khi xoay, điều chỉnh vị trí để vẽ từ tâm
            spriteAnimation.render(gc, -width / 2, -height / 2, scale, scale); // Vẽ từ trung tâm

            // Khôi phục lại trạng thái GraphicsContext
            gc.restore();
        }
    }

    public Direction intersect(GameObject rec) {

        // recTop : duong thang phia tren tao len HCN
        double recTop = rec.getY() +  rec.getHeight();
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
            return Direction.none;
        }

        if(overlapX >= overlapY) {
            if(pYBall >= pYRec) {
                return Direction.down;
            }
            else return Direction.top;
        }
        else {
            if(pXBall >= pXRec) return Direction.right;
            else return Direction.left;
        }
    }

    public void bounceOf(GameObject obj) {

        Direction dir = this.intersect(obj);
        if(obj instanceof Paddle && dir != Direction.top) {
            return;
        }
        switch (dir) {
            case top:
                this.setVelocityY(-getVelocityY());
                break;
            case down:
                this.setVelocityY(-getVelocityY());
                break;
            case right:
                this.setVelocityX(-getVelocityX());
                break;
            case left:
                this.setVelocityX(-getVelocityX());
                break;
        }

    }

    @Override
    public  void reset() {
        setX(Info.BallX);
        setY(Info.BallY);
        setHold(true);
        setVelocityX(0);
        setVelocityY(0);
    }

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean hold) {
        isHold = hold;
    }
}
