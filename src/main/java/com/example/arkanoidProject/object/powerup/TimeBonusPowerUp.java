package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.util.Config;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TimeBonusPowerUp extends PowerUp {

    public TimeBonusPowerUp(double x, double y, Image spriteSheet) {
        super(x, y, 30, 30,
                spriteSheet,
                spriteSheet != null ? 4 : 1,
                1,
                spriteSheet != null ? 120 : 0,
                spriteSheet != null ? 30 : 0,
                0.15,
                2, 2, 26, 26,
                Color.BROWN);
    }

    public TimeBonusPowerUp(double x, double y) {
        this(x, y, null);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        // Giảm thời gian (bonus)
        double newTime = Math.max(0, context.getTimeSeconds() - Config.TIME_BONUS_SECONDS);
        context.setTimeSeconds(newTime);
    }

    @Override
    public String getName() {
        return "Time Bonus";
    }

    @Override
    public String getSymbol() {
        return "T"; // T for Time
    }
}