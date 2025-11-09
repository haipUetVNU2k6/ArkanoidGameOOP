package com.example.arkanoidProject.object.powerup;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ExtraLifePowerUp extends PowerUp {

    public ExtraLifePowerUp(double x, double y, Image spriteSheet) {
        super(x, y, 30, 30,
                spriteSheet,
                spriteSheet != null ? 4 : 1,
                1,
                spriteSheet != null ? 120 : 0,
                spriteSheet != null ? 30 : 0,
                0.15,
                2, 2, 26, 26,
                Color.RED);
    }

    public ExtraLifePowerUp(double x, double y) {
        this(x, y, null);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        context.setLives(context.getLives() + 1);
    }

    @Override
    public String getName() {
        return "Extra Life";
    }

    @Override
    public String getSymbol() {
        return "♥"; // Heart symbol
    }
}