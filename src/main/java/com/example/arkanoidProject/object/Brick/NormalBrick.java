package com.example.arkanoidProject.object.Brick;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;

public class NormalBrick extends Brick {

    public NormalBrick(double x, double y, double width, double height, Image spriteSheet, int frameWidth, int frameHeight,
                       int columns, int rows, double frameDuration) {
        super(x, y, width, height,
                spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration, 1);
    }
    public static void main(String[] args) {

    }
}
