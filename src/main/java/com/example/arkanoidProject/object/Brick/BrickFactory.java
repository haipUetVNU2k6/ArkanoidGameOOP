package com.example.arkanoidProject.object.Brick;

import com.example.arkanoidProject.util.Info;
import javafx.scene.image.Image;

public class BrickFactory {
    public static Brick createBrick(Info.BrickType type, double x, double y, double width, double height, Image spriteSheet, int frameWidth, int frameHeight,
                              int columns, int rows, double frameDuration) {
        switch(type) {
            case NORMAL:
                return new NormalBrick(x,y,width,height,spriteSheet,frameWidth,frameHeight,columns,rows,frameDuration);
            case STRONG:
                return new StrongBrick(x,y,width,height,spriteSheet,frameWidth,frameHeight,columns,rows,frameDuration);
            default:
                return null;
        }
    }
    public static void main(String[] args) {

    }
}
