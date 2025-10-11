package Game.Manage;

import Game.Object.Ball;
import Game.Object.Brick;
import Game.Object.Paddle;

import java.util.ArrayList;
import java.util.List;

public class GameManager  {
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
        WIN
    }
    public GameManager() {
        gameState = GameState.MENU;
    }
    public void startGame() {
        this.bricks.clear();
        this.gameState = GameState.PLAYING;
        this.paddle = new Paddle(WIDTH/2-50,HEIGHT-50,100,20,5);
        this.ball   = new Ball(WIDTH/2-10,HEIGHT/2-10,10,10,0,0);
        this.scores = 0;
        this.lives = 3;

        int x=0,y=0;
        for(int i=0;i<5;++i) {
            for(int j=0;j<10;++j) {
                Brick newBrick = new Brick(x + j*WIDTH,y + i*HEIGHT,60,80,1,1);
                bricks.add(newBrick);
            }
        }
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {
            this.paddle.update();
            this.ball.update();
            checkCollision();
        }
        else {
            return ;
        }
        if(bricks.isEmpty()) {
            gameState = GameState.WIN;
        }

    }

    public void checkCollision() {
        // Ball va Paddle
        ball.bounceOf(paddle);
        //Ball va Brick
        for(Brick obj:bricks) {
            ball.bounceOf(obj);
            obj.takeHit(1);
            if(obj.isDestroyed()) bricks.remove(obj);
        }
        //Ball va 4 edge screen top/left/right
        if(ball.getX() < 0 || ball.getX() > WIDTH) {
            ball.setDirectionX(-ball.getDirectionX());
        }
        if(ball.getY() < 0 ) {
            ball.setDirectionY(-ball.getDirectionY());
        }
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
