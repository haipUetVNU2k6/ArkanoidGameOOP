package com.example.arkanoidProject.object.Brick;

import com.example.arkanoidProject.util.Info;
import javafx.scene.image.Image;

public class TNTBrick extends  Brick{

    public TNTBrick(double x, double y, double width, double height, Image spriteSheet, int frameWidth, int frameHeight,
                       int columns, int rows, double frameDuration) {
        super(x, y, width, height,
                spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration, 1);
    }

    @Override
    public Info.BrickType getType() {
        return Info.BrickType.TNT;
    }
    public static void main(String[] args) {

    }
}
