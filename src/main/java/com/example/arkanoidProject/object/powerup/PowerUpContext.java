package com.example.arkanoidProject.object.powerup;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.Paddle;
import java.util.List;

public class PowerUpContext {
    private List<Ball> balls;
    private Ball mainBall;
    private Paddle mainPaddle;
    private List<Paddle> sidePaddles;
    private int lives;
    private double timeSeconds;
    private double[] triplePaddleTimer;

    public PowerUpContext(List<Ball> balls, Ball mainBall, Paddle mainPaddle,
                          List<Paddle> sidePaddles, int lives, double timeSeconds,
                          double[] triplePaddleTimer) {
        this.balls = balls;
        this.mainBall = mainBall;
        this.mainPaddle = mainPaddle;
        this.sidePaddles = sidePaddles;
        this.lives = lives;
        this.timeSeconds = timeSeconds;
        this.triplePaddleTimer = triplePaddleTimer;
    }

    // Getters
    public List<Ball> getBalls() { return balls; }
    public Ball getMainBall() { return mainBall; }
    public Paddle getMainPaddle() { return mainPaddle; }
    public List<Paddle> getSidePaddles() { return sidePaddles; }
    public int getLives() { return lives; }
    public double getTimeSeconds() { return timeSeconds; }
    public double[] getTriplePaddleTimer() { return triplePaddleTimer; }

    // Setters
    public void setLives(int lives) { this.lives = lives; }
    public void setTimeSeconds(double timeSeconds) { this.timeSeconds = timeSeconds; }
}