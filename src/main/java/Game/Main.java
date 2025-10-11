package Game;

import Game.Manage.GameManager;
import Game.GameView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class Main extends Application {
    public static boolean start = false;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    @Override
    public void start(Stage primaryStage) {
        GameManager gameManager = new GameManager();
        GameView gameView = new GameView(gameManager);

        Canvas canvas = new Canvas(GameManager.WIDTH, GameManager.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        // Handle keyboard input
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        // Game Loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Input handling
                if (activeKeys.contains(KeyCode.LEFT)) {
                    gameManager.getPaddle().moveLeft();
                } else if (activeKeys.contains(KeyCode.RIGHT)) {
                    gameManager.getPaddle().moveRight();
                }
                  else if(GameManager.start == false && activeKeys.contains(KeyCode.ENTER)) {
                      gameManager.getBall().setDirectionY(-1);
                      gameManager.getBall().setDirectionX(1.2);
                      GameManager.start = true;
                }

                // Update game state
                gameManager.updateGame();

                // Render the game
                gameView.render(gc);
            }
        }.start();

        primaryStage.setTitle("Arkanoid OOP");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
