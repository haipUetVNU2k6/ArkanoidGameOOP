package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.util.Config;
import javafx.scene.image.Image;
import java.util.Random;

public abstract class PowerUpFactory {
    private static final Random random = new Random();

    // Cache sprites (sẽ load 1 lần)
    private static Image extraBallSprite;
    private static Image triplePaddleSprite;
    private static Image extraLifeSprite;
    private static Image timeBonusSprite;

    static {
        try {
            // Uncomment khi có sprite files
            // extraBallSprite = new Image(PowerUpFactory.class.getResource("/com/example/arkanoidProject/view/images/powerup/extraball.png").toExternalForm());
            // triplePaddleSprite = new Image(PowerUpFactory.class.getResource("/com/example/arkanoidProject/view/images/powerup/triplepaddle.png").toExternalForm());
            // extraLifeSprite = new Image(PowerUpFactory.class.getResource("/com/example/arkanoidProject/view/images/powerup/extralife.png").toExternalForm());
            // timeBonusSprite = new Image(PowerUpFactory.class.getResource("/com/example/arkanoidProject/view/images/powerup/timebonus.png").toExternalForm());
        } catch (Exception e) {
            System.out.println("PowerUp sprites not found, using fallback colors");
        }
    }

    /**
     * Tạo PowerUp ngẫu nhiên dựa trên tỉ lệ weight
     */
    public static PowerUp createRandomPowerUp(double x, double y) {
        int roll = random.nextInt(100);

        if (roll < Config.EXTRA_BALL_WEIGHT) {
            return new ExtraBallPowerUp(x, y, extraBallSprite);
        } else if (roll < Config.EXTRA_BALL_WEIGHT + Config.TRIPLE_PADDLE_WEIGHT) {
            return new TriplePaddlePowerUp(x, y, triplePaddleSprite);
        } else if (roll < Config.EXTRA_BALL_WEIGHT + Config.TRIPLE_PADDLE_WEIGHT + Config.EXTRA_LIFE_WEIGHT) {
            return new ExtraLifePowerUp(x, y, extraLifeSprite);
        } else {
            return new TimeBonusPowerUp(x, y, timeBonusSprite);
        }
    }
}