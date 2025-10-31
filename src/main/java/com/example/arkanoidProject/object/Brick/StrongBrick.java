package com.example.arkanoidProject.object.Brick;

import com.example.arkanoidProject.levels.LevelManager;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

import java.io.IOException;

public class StrongBrick extends Brick{

    public StrongBrick(double x, double y, double width, double height, Image spriteSheet, int frameWidth, int frameHeight,
                       int columns, int rows, double frameDuration) {
        super(x, y, width, height,
                spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration, 2);
    }

    @Override
    public void takeHit(int amount) {
        setHitPoints(getHitPoints() - amount);
        if(getHitPoints() == 1) {
            Image normalBrick = null;
            try {
                normalBrick = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/normalbrick.png").toExternalForm());
            }
            catch (Exception IOException) {
                System.out.println("Error:load brick");
            }
            setSpriteAnimation(new SpriteAnimation(normalBrick
            ,spriteAnimation.getFrameWidth(), spriteAnimation.getFrameHeight()
                    ,spriteAnimation.getRows(), spriteAnimation.getColumns(),spriteAnimation.getFrameDuration()));
        }
        return;
    }
    public static void main(String[] args) {

    }
}
