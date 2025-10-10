package Game.Manage;

import Game.AbstractObject.GameObject;
import Game.Object.Ball;
import Game.Object.Paddle;
import Game.Object.Brick;
import com.example.arkanoidgameoop.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameManager  {
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private int scores;
    private int lives;
    private GameState gameState;
    public  static final int HEIGHT = 800;
    public  static final int WIDTH  = 600;
    public enum GameState {
        MENU,
        PLAYING,
        PAUSED,
        GAME_OVER
    }
    public GameManager() {
        gameState = GameState.MENU;
    }
    public void startGame() {
        this.paddle = new Paddle(WIDTH/2,0,50,20,5);
        this.ball   = new Ball(WIDTH/2,20+5,5,10,0,1);
        this.scores = 0;
        this.lives = 3;
        this.gameState = GameState.PLAYING;
        this.bricks.clear();
    }

    public void updateGame() {
        if(gameState == GameState.PLAYING) {
            this.paddle.update();
            this.ball.update();
            //  if va cham bien tren Screen : bounceoff() -> tham so nen la gi?
            //
        }


    }

    public static void main(String[] args) {
     GameManager arkanoid  = new GameManager();
     arkanoid.startGame();
    }
}
