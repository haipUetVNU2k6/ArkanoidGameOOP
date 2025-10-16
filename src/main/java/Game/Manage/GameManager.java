package Game.Manage;

import Game.Level.*;
import Game.Main;
import Game.Object.*;


import java.util.ArrayList;
import java.util.Iterator;


public class GameManager  {
    public static boolean start = false;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private int scores;
    private int lives;
    private GameState gameState;
    private Map map;
    public  static final double HEIGHT = 600;
    public  static final double WIDTH  = 800;
    public  String powerUp;
    public enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        GAME_OVER,
        WIN;
    }
    public GameManager() {
        gameState = GameState.PLAYING;
        startGame();
    }
    public void startGame() {
        this.bricks.clear();
        this.gameState = GameState.PLAYING;
        this.paddle = new Paddle(Paddle.startX,Paddle.startY,Paddle.WIDTH,Paddle.HEIGHT,3);
        this.ball   = new Ball(Ball.startX,Ball.startY,Ball.r,2.5,0,0);
        this.scores = 0;
        this.lives = 3;

//        for(int i=0;i<8;++i) {
//            for(int j=0;j<10;++j) {
//                Brick newBrick = new Brick(j*(GameManager.WIDTH/10),i*(GameManager.HEIGHT/20),80,30,1,1);
//                bricks.add(newBrick);
//            }
//
//        }
        map = new MapOne();
        bricks = map.getMap();
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {


            checkCollision();
            this.paddle.update();
            this.ball.update();
            //System.out.println(bricks.size());
           if(bricks.isEmpty()) {
                gameState = GameState.WIN;
            }
        }
        else {
            return ;
        }

    }

    public void checkCollision() {
        // Ball va Paddle

        if(start == true) {

            ball.bounceOf(paddle);

        }
        else {
            System.out.println(ball.getX()+","+ paddle.getX()+"," +paddle.getWidth());
            double paddleOldX = ball.getX() - paddle.getWidth()/2;
            double diff = paddle.getX() - paddleOldX;
            if(diff>0) {
                ball.setX(ball.getX() + paddle.getSpeed());
            }
            else if(diff<0){
                ball.setX(ball.getX() - paddle.getSpeed());
            }
        }

        //Ball va Brick
        /*for(Brick obj:bricks) {
            ball.bounceOf(obj);
            obj.takeHit(1);
            if(obj.isDestroyed()) bricks.remove(obj);
        }*/
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
                Brick brick = iterator.next();
                ball.bounceOf(brick);
                if(ball.isCollision == true) {
                    brick.takeHit(1);
                    ball.isCollision = false;
                }
                if (brick.isDestroyed()) {
                    // TNT no
                    if (brick.id == 3) {
                        double bx = brick.getX();
                        double by = brick.getY();
                        double bw = brick.getWidth();
                        double bh = brick.getHeight();
                        for (Brick b : bricks) {
                            if (b != brick) {
                                if (b.getX() >= bx - bw && b.getX() <= bx + bw &&
                                        b.getY() >= by - bh && b.getY() <= by + bh) {
                                    b.takeHit(2);
                                }
                            }
                        }
                    }
                    iterator.remove();
                    scores += 10;
                }
            }

        //Ball va 4 edge screen top/left/right

        if(ball.getY() >= HEIGHT) {
            if(this.lives > 0 ) {
                startGame();
                this.lives = this.lives - 1;
                start = false;
            }
            else {
                gameState = GameState.GAME_OVER;
                return;
            }
        }
    }

    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public ArrayList<Brick> getBricks() { return bricks; }
    public int getScore() { return scores; }
    public int getLives() { return lives; }
    public GameState getGameState() { return gameState; }

    public static void main(String[] args) {
     GameManager arkanoid  = new GameManager();
     arkanoid.startGame();
    }
}
