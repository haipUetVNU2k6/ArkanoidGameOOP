package Game.Manage;

import Game.Level.*;
import Game.Main;
import Game.Object.*;


import java.util.ArrayList;
import java.util.Iterator;


public class GameManager  {
    public static boolean start = false;
    private Level level;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    private static final GameManager instance = new GameManager();
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private int scores;
    private int lives;
    private GameState gameState;
    public  static final double HEIGHT = 600;
    public  static final double WIDTH  = 800;
    public  String powerUp;
    public enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        GAME_OVER,
        WIN,
        SETTINGS;
    }
    private GameManager() {
        gameState = GameState.MENU;
      //  startGame();
    }

    public static GameManager getInstance() {
        return instance;
    }
    public void startGame() {
        this.bricks.clear();
        this.gameState = GameState.PLAYING;
        this.paddle = new Paddle(Paddle.startX,Paddle.startY,Paddle.WIDTH,Paddle.HEIGHT,3);
        this.ball   = new Ball(Ball.startX,Ball.startY,Ball.r,1.3,0,0);
        this.scores = 0;
        this.lives = 3;
        level = new Level(1);
        String path = "/MatrixLevel/matrix";
        path = path + Integer.toString(level.getId());
        path = path + ".txt";
        level.setPath(path);
        level.loadLevel();
        this.bricks = level.getBricks();
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {


            checkCollision();
            this.paddle.update();
            this.ball.update();
            //System.out.println(bricks.size());
           if(bricks.isEmpty()) {
               if(level.getId() >= Level.maxID) gameState = GameState.WIN;
               else {
                   level.setId(level.getId()+1);
                   String path = "/MatrixLevel/matrix" + Integer.toString(level.getId()) + ".txt";
                   level.setPath(path);
                   level.loadLevel();
                   paddle.reset();
                   ball.reset();
               }


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
           // System.out.println(ball.getX()+","+ paddle.getX()+"," +paddle.getWidth());
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
                    switch (brick.getId()) {
                        case 1:
                            brick.takeHit(1);
                            break;
                        case 2:
                            brick.takeHit(1);
                            break;
                        case 3:
                            brick.takeHit(1);
                           // System.out.println(brick.getHitPoints());
                            break;
                    }
                    ball.isCollision = false;
                }
               // System.out.println(brick.getHitPoints());
                if (brick.isDestroyed()) {
                    // TNT no
                    if (brick.getId() == 2) {
                        double bx = brick.getX();
                        double by = brick.getY();
                        double bw = brick.getWidth();
                        double bh = brick.getHeight();
                        for (Brick b : bricks) {
                            if (b != brick) {
                                if (b.getX() >= bx - bw && b.getX() <= bx + bw &&
                                        b.getY() >= by - bh && b.getY() <= by + bh) {
                                    b.takeHit(b.getHitPoints());
                                }
                            }
                        }
                    }

                    iterator.remove();
                    scores += 10;
                }
            }

        //Ball collision 4 edge screen top/left/right

        if(ball.getY() >= HEIGHT) {
            if(this.lives > 0 ) {
               paddle.reset();
               ball.reset();
               start = false;
               this.lives--;
            }
            else {
                gameState = GameState.GAME_OVER;
                return;
            }
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }
    public Ball getBall() {
        return ball;
    }
    public ArrayList<Brick> getBricks() {
        return bricks;
    }
    public int getScore() {
        return scores;
    }
    public int getLives() {
        return lives;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public static void main(String[] args) {
     GameManager arkanoid  = new GameManager();
     arkanoid.startGame();
    }
}
