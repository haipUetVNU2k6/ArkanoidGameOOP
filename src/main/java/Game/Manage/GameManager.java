package Game.Manage;

import Game.Main;
import Game.Object.Ball;
import Game.Object.Brick;
import Game.Object.Paddle;

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
    public  static final int HEIGHT = 800;
    public  static final int WIDTH  = 600;
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
       // this.bricks.clear();
        this.gameState = GameState.PLAYING;
        this.paddle = new Paddle(WIDTH/2-50,HEIGHT-50,100,20,3);
        double radius = 5;
        this.ball   = new Ball(paddle.getX()+paddle.getWidth()/2,paddle.getY()-2*radius,radius,1.3,0,0);
        this.scores = 0;
        this.lives = 3;

        int x=0,y=0;
        for(int i=0;i<5;++i) {
            for(int j=0;j<10;++j) {
                Brick newBrick = new Brick(x + j*60,y + i*80,60,80,1,1);
                bricks.add(newBrick);
            }

        }
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {


            checkCollision();
            this.paddle.update();
            this.ball.update();
            //System.out.println(bricks.size());
           /* if(bricks.isEmpty()) {
                gameState = GameState.WIN;
            }*/
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
                    iterator.remove();
                    scores += 10;
                }
            }

        //Ball va 4 edge screen top/left/right

        if(ball.getY() >= HEIGHT) {
            gameState = GameState.GAME_OVER;
            return;
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
