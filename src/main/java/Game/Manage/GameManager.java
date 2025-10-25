package Game.Manage;

import Game.Level.Level;
import Game.Object.Ball;
import Game.Object.Brick.Brick;
import Game.Object.Paddle;
import Game.Object.PowerUp.PowerUp;
import Game.Object.PowerUp.PowerUpFactory;

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

    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
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

    public static boolean isStart() {
        return start;
    }

    public int getScores() {
        return scores;
    }

    public String getPowerUp() {
        return powerUp;
    }

    public static void setStart(boolean start) {
        GameManager.start = start;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setPowerUp(String powerUp) {
        this.powerUp = powerUp;
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {
           // System.out.println(paddle.getDirectionX()+","+paddle.getDirectionY());
            this.paddle.update();
            this.ball.update();

            checkCollision();
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
                   start = false;
               }


            }
        }
        else {
            return ;
        }

    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void checkCollision() {
        // Ball va Paddle

        if(start == true) {

            ball.bounceOf(paddle);

        }
        else {
            double diff = paddle.getX() - paddle.getOldX();
            System.out.println(paddle.getX() + "," + paddle.getOldX());
           // System.out.println(ball.getX()+","+ paddle.getX()+"," + paddleOldX);
            if(diff != 0) {
                ball.setX(ball.getX() + diff);
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
                           // brick = new NormalBrick(brick.getX(),ball.getY(),ball.getWidth(),ball.getHeight());
                            break;
                    }
                    ball.isCollision = false;
                }
               // System.out.println(brick.getHitPoints());
                if (brick.isDestroyed()) {

                   if(brick.getId() == 3) {
                       PowerUp newPower = PowerUpFactory.createRandomPowerUp(brick.getX(), brick.getY());
                       if (newPower != null) powerUps.add(newPower);
                   }

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

        // paddle vs powerUp
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
