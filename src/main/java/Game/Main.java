package Game;

import Game.Manage.GameManager;
import Game.Util.MenuScene;
import Game.Util.SettingScene;
import Game.Util.Button;
import Game.Util.Util;
import Game.View.GameView;
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
    private final Set<KeyCode> activeKeys = new HashSet<>();

    @Override
    public void start(Stage primaryStage) {
        GameManager gameManager = GameManager.getInstance();
        GameView gameView = new GameView();
        MenuScene menuScene = new MenuScene();
        SettingScene settingScene = new SettingScene();

        Canvas canvas = new Canvas(GameManager.WIDTH, GameManager.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        root.getStyleClass().add("game-container");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        // Handle keyboard input
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        // Xử lý di chuột để tạo hiệu ứng hover
        scene.setOnMouseMoved(e -> {
            if (gameManager.getGameState() == GameManager.GameState.MENU) {
                menuScene.checkHover(e);
            } else if (gameManager.getGameState() == GameManager.GameState.SETTINGS) {
                settingScene.checkHover(e);
            }
        });

        // Xử lý click chuột
        scene.setOnMouseClicked(e -> {
            switch (gameManager.getGameState()) {
                case MENU:
                    if (menuScene.start.isClicked(e)) {
                        gameManager.startGame(); // Bắt đầu game!
                    } else if (menuScene.settingClick(e)) {
                        gameManager.setGameState(GameManager.GameState.SETTINGS);
                    } else if (menuScene.exitClick(e)) {
                        primaryStage.close(); // Đóng ứng dụng
                    }
                    break;
                case SETTINGS:
                    if (settingScene.exitClicked(e)) {
                        gameManager.setGameState(GameManager.GameState.MENU);
                    }
                    break;
                // Có thể thêm xử lý click cho GAME_OVER/WIN ở đây
                // ví dụ: click để quay về MENU
            }
        });
        // Game Loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 1. Xóa màn hình một lần duy nhất
                gc.clearRect(0, 0, GameManager.WIDTH, GameManager.HEIGHT);

                // 2. Sử dụng switch để "định tuyến"
                switch (gameManager.getGameState()) {
                    case MENU:
                        menuScene.drawMenuScene(gc);
                        break;

                    case SETTINGS:
                        settingScene.drawSettingScene(gc);
                        break;

                    case PLAYING:
                        // --- Toàn bộ logic input và update cũ của bạn
                        // --- bây giờ được chuyển vào đây

                        // Input handling
                        if (activeKeys.contains(KeyCode.LEFT)) {
                            gameManager.getPaddle().moveLeft();
                        } else if (activeKeys.contains(KeyCode.RIGHT)) {
                            gameManager.getPaddle().moveRight();
                        }
                        else if(activeKeys.contains(KeyCode.ESCAPE)) {
                            gameManager.setGameState(GameManager.GameState.SETTINGS);
                        }

                        if (GameManager.start == false && activeKeys.contains(KeyCode.ENTER)) {
                            gameManager.getBall().setDirectionY(-1);
                            gameManager.getBall().setDirectionX(1.2);
                            GameManager.start = true;
                        }

                        // Update game state
                        gameManager.updateGame();

                        // Render the game
                        gameView.render(gc);
                        break;

                    case GAME_OVER:
                    case WIN:
                        // GameView cũng xử lý các màn hình này
                        gameView.render(gc);
                        break;
                }
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
