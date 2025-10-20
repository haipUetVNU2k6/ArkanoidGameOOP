package Game.View;

import Game.Manage.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {

    public GameView() {}

    public void render(GraphicsContext gc) {
        GameManager gameManager = GameManager.getInstance();
       // gc.clearRect(0, 0, GameManager.WIDTH, GameManager.HEIGHT);

        if (gameManager.getGameState() == GameManager.GameState.PLAYING) {
            // Render objects
            gameManager.getPaddle().render(gc);
            gameManager.getBall().render(gc);
            gameManager.getBricks().forEach(brick -> brick.render(gc));

            // Render HUD
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 20));
            gc.fillText("Score: " + gameManager.getScore(), 10, 30);
            gc.fillText("Lives: " + gameManager.getLives(), GameManager.WIDTH - 80, 30);
        } else if (gameManager.getGameState() == GameManager.GameState.GAME_OVER) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 50));
            gc.fillText("GAME OVER", GameManager.WIDTH / 2 - 150, GameManager.HEIGHT / 2);
        } else if (gameManager.getGameState() == GameManager.GameState.WIN) {
            gc.setFill(Color.GREEN);
            gc.setFont(new Font("Arial", 50));
            gc.fillText("YOU WIN!", GameManager.WIDTH / 2 - 120, GameManager.HEIGHT / 2);
        }
    }
}
