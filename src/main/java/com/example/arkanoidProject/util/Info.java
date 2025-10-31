package com.example.arkanoidProject.util;

public interface Info {
    public static final int ScreenWidth = 700;
    public static final int ScreenHeight = 800;

    public static double paddleWidth = 70;
    public static double paddleHeight = 56;
    public static double startPaddleX = ScreenWidth / 2 - paddleWidth / 2;
    public static double startPaddleY = ScreenHeight - paddleHeight;
    public static double paddleSpeed = 400;
    public static double paddleHitBoxOffsetX = 0;
    public static double paddleHitBoxOffsetY = 0;
    public static double paddleHitBoxW = 70;
    public static double paddleHitBoxH = 19;

    public static double ballWidth = 36;
    public static double ballHeight = 21;

    public static int startBallDx = 100;
    public static int startBallDy = 100;
    public static double ballHitBoxOffsetX = 18;
    public static double ballHitBoxOffsetY = 3;
    public static double ballHitBoxW = 18;
    public static double ballHitBoxH = 15;
    public static double startBallX = startPaddleX + paddleHitBoxOffsetX + paddleHitBoxW / 2 -
            ballHitBoxW / 2 - ballHitBoxOffsetX;
    public static double startBallY = startPaddleY + paddleHitBoxOffsetY - ballHitBoxH -
            ballHitBoxOffsetY + 1; // +1 để khiến ball và paddle va chạm để ball thay đổi render
    public static double ballDxMultiple = 8;
    public static double ballDyMultiple = 24;
}
