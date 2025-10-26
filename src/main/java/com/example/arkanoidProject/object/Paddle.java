package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Info;
import com.example.arkanoidProject.util.SpriteAnimation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Paddle extends MoveableObject {
    private double screenWidth;
    private Set<KeyCode> keysPressed = new HashSet<>();
    public static Image img = new Image(Paddle.class.getResource("/com/example/arkanoidProject/view/images/PaddleX.png").toExternalForm());
    private double speed = 400;

    public Paddle(double x, double y, double width, double height, Image spriteSheet, int columns, int rows,
                  int frameWidth, int frameHeight, double frameDuration, double screenWidth) {
        super(x, y, width, height,
                new SpriteAnimation(spriteSheet, frameWidth, frameHeight, columns, rows, frameDuration));
        this.screenWidth = screenWidth;
    }

    public Paddle(double x, double y, double width, double height,int screenWidth) {
        super(x, y, width, height);
        this.screenWidth = screenWidth;
    }

    public Set<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    @Override
    public void update(double dt) {

        // Cập nhật vận tốc theo phím nhấn

        velocityX = 0;
        if (keysPressed.contains(KeyCode.A)) {
            velocityX = -speed;
        }
        if (keysPressed.contains(KeyCode.D)) {
            velocityX = speed;
        }

        super.update(dt);
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

    @Override
    public  void reset() {
        setX(Info.PaddleX);
        setY(Info.PaddleY);
    }

    @Override
    public  void render(GraphicsContext gc) {
        if(img != null) {
            gc.drawImage(img,getX(),getY(),getWidth(),getHeight());
        }
        else {
            gc.setFill(Color.BLUE);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
