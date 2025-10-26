package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Paddle extends MoveableObject {

    private double screenWidth;
    private Set<KeyCode> keysPressed = new HashSet<>();

    private double speed = 400;

    public Paddle(double x, double y, double width, double height, Image spriteSheet, int columns, int rows,
                  int frameWidth, int frameHeight, double frameDuration, double screenWidth) {
        super(x, y, width, height,
                new SpriteAnimation(spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration));
        this.screenWidth = screenWidth;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        // Cập nhật vận tốc theo phím nhấn
        velocityX = 0;
        if (keysPressed.contains(KeyCode.LEFT)) {
            velocityX = -speed;
        } else if (keysPressed.contains(KeyCode.RIGHT)) {
            velocityX = speed;
        }

        // Giới hạn di chuyển trong màn hình
        if (x < 0) x = 0;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    // Quản lý phím nhấn từ controller bên ngoài
    public void pressKey(KeyCode key) {
        keysPressed.add(key);
    }

    public void releaseKey(KeyCode key) {
        keysPressed.remove(key);
    }
}
