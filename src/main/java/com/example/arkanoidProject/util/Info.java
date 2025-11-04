package com.example.arkanoidProject.util;

public interface Info {
    public static final int ScreenWidth = 700;
    public static final int ScreenHeight = 800;

    public static final int PaddleWidth = 100;
    public static final int PaddleHeight = 30;
    public static final double PaddleX = ScreenWidth / 2 - 50;
    public static final double PaddleY = ScreenHeight - 50;
    public static final double PaddleSpeed = 400;

    public static final double BallDiameter = 50;
    public static final double BallX = PaddleX + (PaddleWidth - BallDiameter)/2 - 10;
    public static final double BallY = PaddleY - BallDiameter;

    public  enum Direction {
        top,down,
        left,right,none;
    }

    public enum BrickType {
        NORMAL, STRONG,
        TNT
    }
    public static void main(String[] args) {

    }
}
