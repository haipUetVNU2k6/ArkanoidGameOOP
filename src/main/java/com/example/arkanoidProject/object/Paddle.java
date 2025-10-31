package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Info;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Paddle extends MoveableObject {
    private Set<KeyCode> keysPressed = new HashSet<>();

    private double speed = Info.paddleSpeed;

    public Paddle(double x, double y, double width, double height,
                  Image spriteSheet, int columns, int rows,
                  int frameWidth, int frameHeight, double frameDuration,
                  double hitBoxOffsetX, double hitBoxOffsetY, double hitBoxW, double hitBoxH) {
        super(x, y, width, height,
                new SpriteAnimation(spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration),
                hitBoxOffsetX, hitBoxOffsetY, hitBoxW, hitBoxH
                );
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        // Cập nhật vận tốc theo phím nhấn
        dx = 0;
        if (keysPressed.contains(KeyCode.LEFT)) {
            dx = -speed;
        } else if (keysPressed.contains(KeyCode.RIGHT)) {
            dx = speed;
        }

        // Giới hạn di chuyển trong màn hình
        if (x < 0) x = 0;
        if (x + width > Info.ScreenWidth) x = Info.ScreenWidth - width;
    }
}
