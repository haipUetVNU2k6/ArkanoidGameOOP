package com.example.arkanoidProject.util;

public interface Info {
    public static final int ScreenWidth = 600;
    public static final int ScreenHeight = 800;

    public static final int PaddleWidth = 200;
    public static final int PaddleHeight = 40;
    public static final double PaddleX = 250;
    public static final double PaddleY = 750;
    public static final double PaddleSpeed = 400;

    public static final double BallDiameter = 50;
    public static final double BallX = PaddleX + (PaddleWidth - BallDiameter)/2 ;
    public static final double BallY = PaddleY - BallDiameter;
    public static void main(String[] args) {

    }
}
