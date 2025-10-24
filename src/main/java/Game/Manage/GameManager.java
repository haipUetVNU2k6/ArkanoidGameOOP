package Game.Manage;

import Game.Level.Level;
import Game.Level.Map;
import Game.Object.*;
import Game.Object.PowerUp;
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
    private Map map;
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
            Iterator<PowerUp> it = powerUps.iterator();
            while (it.hasNext()) {
                PowerUp p = it.next();
                p.update();
                if (p.getY() > HEIGHT) {
                    it.remove(); // rơi khỏi màn hình
                }
                if (p.collidesWith(paddle)) {
                    paddle.setWidth(paddle.newWidth);
                    paddle.update();
                }
            }
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
                    brick.takeHit(1);
                    ball.isCollision = false;
                }
                if (brick.isDestroyed()) {
                    // TNT no
                    PowerUp newPower = PowerUpFactory.createRandomPowerUp(brick.getX(), brick.getY());
                    if (newPower != null) powerUps.add(newPower);

                    if (brick.getId() == 2) {
                        System.out.println("in here.");
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

        //Ball va 4 edge screen top/left/right

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
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
    public static void main(String[] args) {
     GameManager arkanoid  = new GameManager();
     arkanoid.startGame();
    }
}
