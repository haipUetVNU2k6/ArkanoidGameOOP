package com.example.arkanoidProject.util;

public class Config {
    private static int lives = 1;
    public static int getLives() {
        return lives;
    }

    // ====== SCREEN ======
    private static int screenWidth = 700;
    private static int screenHeight = 800;

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    // ====== PADDLE ======
    public static double paddleWidth = 70;
    public static double paddleHeight = 56;
    public static double paddleSpeed = 400;

    public static double paddleHitBoxOffsetX = 0;
    public static double paddleHitBoxOffsetY = 0;
    public static double paddleHitBoxW = 70;
    public static double paddleHitBoxH = 19;

    public static double getStartPaddleX() {
        return getScreenWidth() / 2.0 - paddleWidth / 2.0;
    }

    public static double getStartPaddleY() {
        return getScreenHeight() - paddleHeight;
    }


    // ====== BALL ======
    public static int startBallDx = 100;
    public static int startBallDy = 100;

    public static double ballWidth = 36;
    public static double ballHeight = 21;

    public static double ballHitBoxOffsetX = 18;
    public static double ballHitBoxOffsetY = 3;
    public static double ballHitBoxW = 18;
    public static double ballHitBoxH = 15;

    public static double getStartBallX() {
        return getStartPaddleX()
                + paddleHitBoxW / 2 - ballHitBoxW / 2
                - ballHitBoxOffsetX;
    }

    public static double getStartBallY() {
        return getStartPaddleY()
                - ballHitBoxH - ballHitBoxOffsetY
                + 1; // +1 để đảm bảo va chạm frame đầu
    }

    public static double ballDxMultiple = 8;
    public static double ballDyMultiple = 24;


    // ====== BRICKS ======
    public static int brickWidth = 70;
    public static int brickHeight = 35;

    public static int maxBrickInOneRow() {
        return getScreenWidth() / brickWidth;
    }
    public static int maxBrickInOneCol() {
        return (int) ((getScreenHeight() * 0.4) / brickHeight);
    }

    public static enum PowerUpType {
        EXPAND_PADDLE, SHRINK_PADDLE
    }

    public static final double diameterPowerUp = 20;

}
