package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Brick.Brick;
import com.example.arkanoidProject.object.GameObject;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.state_controller.controller.PlayCtrl;
import com.example.arkanoidProject.util.Info;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {
    private Pane root;
    private PlayCtrl controller;

    private GraphicsContext gc;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks = new ArrayList<>();

    private long lastTime = 0;

    private LevelManager levelManager;
    private int level = 1;
    private int levelToLoad;
    private int lives;
    private int scores;



    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/play.fxml"));
            root = loader.load();
            controller = loader.getController();

            gc = controller.getGameCanvas().getGraphicsContext2D();

            Image ballSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/cry.png").toExternalForm());
            Image paddleSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/paddle.png").toExternalForm());
            //Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/images/brick.png").toExternalForm());

            paddle = new Paddle(Info.PaddleX, Info.PaddleY, Info.PaddleWidth, Info.PaddleHeight, Info.ScreenWidth);
            ball = new Ball(Info.BallX, Info.BallY, Info.BallDiameter, ballSprite, 10, 1, 880, 512, 0.1, Info.ScreenWidth, Info.ScreenHeight);
            //paddle = new Paddle(Info.PaddleX, Info.PaddleY, Info.PaddleWidth, Info.PaddleHeight,paddleSprite, 16, 1, 800, 640, 0.1, Info.ScreenWidth);
            scores = 0;
            lives = 3;
            //System.out.println(ball.getY()+","+ paddle.getY());
            int brickRows = 5;
            int brickCols = 10;
            int brickWidth = 60;
            int brickHeight = 20;

// 1. Load sprite
            Image brickSprite = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick.png").toExternalForm());
// 2. Khởi tạo LevelManager
            levelManager = new LevelManager(brickSprite);
// 3. Lấy user hiện tại
            int levelToLoad = MainApp.userManager.getCurrentUser().getLastLevel();
// 4. Load màn tương ứng với user
            bricks = levelManager.loadLevel(levelToLoad);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        long now = System.nanoTime();
        if (lastTime == 0) {
            lastTime = now;
            return;
        }
        double dt = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        if(ball.isHold()) {
            ball.setX(paddle.getX() + Info.BallX - Info.PaddleX);
        }

        ball.update(dt);
        paddle.update(dt);

        // collision checks...

        // check ball ...
        if(ball.getHeight() + ball.getY() >= Info.ScreenHeight) {
            if(lives > 0) {
                lives--;
                reset();
            }
            else {
                MainApp.stateStack.pop();
                MainApp.stateStack.push(MainApp.menuState);
            }

        }
        Info.Direction direc = intersect(ball,paddle);
        if(direc != Info.Direction.none) {
            ball.bounceOf(paddle,direc);
        }

        // Ball-Brick collision
        for (Brick brick : bricks) {
            Info.Direction dir = intersect(ball, brick) ;
            if (!brick.isDestroyed() && dir != Info.Direction.none) {
                brick.takeHit(1);
                brick.destroy();
                ball.bounceOf(brick,dir);
                if(brick.isDestroyed() == true) {
                    scores += 10;
                }
                break;
            }
        }

        // Kiểm tra nếu tất cả brick bị phá
        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }

        if (allDestroyed) {
            // Cập nhật level hoàn thành cho user
            MainApp.userManager.getCurrentUser().setLastLevel(levelToLoad + 1);
            MainApp.userManager.saveUsers();

            levelManager.nextLevel();
            if (levelManager.hasNextLevel()) {
                bricks = levelManager.loadCurrentLevel();
                reset();
                level++;
            } else {
                System.out.println("You win all levels!");
                MainApp.stateStack.pop();
                MainApp.stateStack.push(MainApp.menuState);
                return;
            }
        }

    }

    @Override
    public void render() {
        gc.clearRect(0, 0, MainApp.WIDTH, MainApp.HEIGHT);

        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        gc.fillText("LEVEL " + level, 20, 20);
        gc.fillText("Scores " + scores, Info.ScreenWidth-60, 20);
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.A ||  event.getCode() == KeyCode.D) {
            paddle.pressKey(event.getCode());
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            lastTime = 0;
            MainApp.stateStack.push(MainApp.pauseState);
        }
        if(ball.isHold() && event.getCode()== KeyCode.ENTER) {
            ball.setVelocityX(0);
            ball.setVelocityY(-200);
            ball.setHold(false);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A ||  event.getCode() == KeyCode.D) {
            paddle.releaseKey(event.getCode());

        }
    }

    @Override
    public Pane getUI() {
        return root;
    }

    public void reset() {
        ball.reset();
        paddle.reset();
    }

    public void restart() {
        reset();
        for(Brick brick:bricks) {
            brick.setDestroyed(false);
        }
        scores = 0;
        lives = 3;
    }

    public Info.Direction intersect(GameObject obj,GameObject rec) {

        // recTop : duong thang phia tren tao len HCN
        double recTop = rec.getY() +  rec.getHeight();
        double recDown = rec.getY() ;
        double recLeft = rec.getX();
        double recRight = rec.getX() + rec.getWidth();



        // ballTop: duong thang phia tren tao len ball
        double objTop = obj.getY() + obj.getHeight();
        double objDown = obj.getY();
        double objLeft = obj.getX();
        double objRight = obj.getX() + obj.getWidth();

        // xac dinh vung chong lan(giao nhau) -> mo sang ben phai chieu duong truc Ox -> lay Min,nguoc lai voi bien trai lay Max
        //overlapX = bien phai - bien trai = max(ballRight,recRight) - min(ballLeft,recLeft).
        double overlapX = Math.min(objRight,recRight) -  Math.max(objLeft,recLeft);
        double overlapY = Math.min(objTop,recTop)  - Math.max(objDown,recDown);

        // overlapX > overlapY -> top/down -> tâm obj < tâm ball -> top
        double pXObj = (obj.getX() + obj.getWidth())/2;
        double pYObj = (obj.getY() + obj.getHeight())/2;
        double pXRec = (rec.getX() + rec.getWidth())/2;
        double pYRec = (rec.getY() + rec.getHeight())/2;

        //System.out.println(ballLeft +","+ recRight +","+ ballRight +","+ recLeft +","+ ballTop +"," + recDown + "," +  ballDown + "," +recTop);
        if(objLeft >= recRight || objRight <= recLeft || objTop <= recDown || objDown >= recTop) {
            return Info.Direction.none;
        }

        if(overlapX >= overlapY) {
            if(pYObj >= pYRec) {
                return Info.Direction.down;
            }
            else return Info.Direction.top;
        }
        else {
            if(pXObj >= pXRec) return Info.Direction.right;
            else return Info.Direction.left;
        }
    }

}


