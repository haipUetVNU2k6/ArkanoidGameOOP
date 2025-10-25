package Game.View;

import Game.Manage.GameManager;
import Game.Object.PowerUp.PowerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {

    public GameView() {}

    public void render(GraphicsContext gc) {
        GameManager gameManager = GameManager.getInstance();

        // Xóa khung vẽ cũ trước khi render khung mới
        gc.clearRect(0, 0, GameManager.WIDTH, GameManager.HEIGHT);

        switch (gameManager.getGameState()) {
            case PLAYING -> {
                // --- Render các đối tượng game ---
                gameManager.getPaddle().render(gc);
                gameManager.getBall().render(gc);
                gameManager.getBricks().forEach(brick -> brick.render(gc));
                gameManager.getPowerUps().forEach(p -> p.render(gc));

                // --- Vẽ đạn ---
                // (Thêm dòng này để hiển thị bullet)
                gameManager.render(gc);

                // --- Hiển thị HUD ---
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial", 20));
                gc.fillText("Score: " + gameManager.getScore(), 10, 30);
                gc.fillText("Lives: " + gameManager.getLives(), GameManager.WIDTH - 100, 30);

                // Hiển thị loại power-up hiện tại nếu có
                PowerUp.Type currentPower = gameManager.getPaddle().getCurrentPowerUp();
                if (currentPower != null) {
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillText("Power-Up: " + currentPower.name(), GameManager.WIDTH / 2 - 60, 30);
                }
            }

            case GAME_OVER -> {
                gc.setFill(Color.RED);
                gc.setFont(new Font("Arial", 50));
                gc.fillText("GAME OVER", GameManager.WIDTH / 2 - 150, GameManager.HEIGHT / 2);
            }

            case WIN -> {
                gc.setFill(Color.GREEN);
                gc.setFont(new Font("Arial", 50));
                gc.fillText("YOU WIN!", GameManager.WIDTH / 2 - 120, GameManager.HEIGHT / 2);
            }

        }
    }
}
