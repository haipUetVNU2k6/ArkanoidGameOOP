package com.example.arkanoidProject.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Sprite animation đơn giản theo hàng: mỗi hàng đại diện cho 1 animation.
 */
public class SpriteAnimation {
    private final Image spriteSheet;
    private final int frameWidth, frameHeight;
    private final int columns, rows;
    private final double defaultFrameDuration;

    private int currentFrame = 0;
    private int currentRow = 0;

    private double timer = 0;
    private double frameDuration;
    private boolean loop = true;
    private boolean running = true;

    private Runnable onFinished;

    private final Map<String, Integer> rowMap = new HashMap<>();

    /**
     * @param spriteSheet ảnh sprite sheet
     * @param frameWidth  chiều rộng mỗi frame
     * @param frameHeight chiều cao mỗi frame
     * @param columns     số cột (frame) mỗi hàng
     * @param rows        số hàng (animation)
     * @param frameDuration thời gian hiển thị mỗi frame (giây)
     */
    public SpriteAnimation(Image spriteSheet, int frameWidth, int frameHeight,
                                   int columns, int rows, double frameDuration) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.columns = columns;
        this.rows = rows;
        this.defaultFrameDuration = frameDuration;
        this.frameDuration = frameDuration;
    }

    /**
     * Cập nhật animation theo thời gian delta (giây).
     */
    public void update(double dt) {
        if (!running) return;

        timer += dt;
        while (timer >= frameDuration) {
            timer -= frameDuration;
            currentFrame++;
            if (currentFrame >= columns) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = columns - 1;
                    running = false;
                    if (onFinished != null) onFinished.run();
                }
            }
        }
    }

    public void render(GraphicsContext gc, double x, double y, double scaleX, double scaleY) {
        int frameX = currentFrame * frameWidth;
        int frameY = currentRow * frameHeight;

        gc.drawImage(spriteSheet,
                frameX, frameY,
                frameWidth, frameHeight,
                x, y,
                frameWidth * scaleX, frameHeight * scaleY);
    }


    /**
     * Chuyển sang animation tại hàng (theo index).
     */
    public void setRow(int row) {
        if (row >= 0 && row < rows && row != currentRow) {
            this.currentRow = row;
            this.currentFrame = 0;
            this.timer = 0;
            this.running = true;
        }
    }

    /**
     * Đăng ký tên cho hàng.
     */
    public void mapRow(String name, int rowIndex) {
        if (rowIndex >= 0 && rowIndex < rows) {
            rowMap.put(name, rowIndex);
        }
    }

    /**
     * Chuyển sang animation theo tên.
     */
    public void setAnimation(String name) {
        Integer row = rowMap.get(name);
        if (row != null) setRow(row);
    }

    // Tiện ích
    public void setLoop(boolean loop) { this.loop = loop; }
    public void setFrameDuration(double seconds) { this.frameDuration = seconds; }
    public void reset() {
        currentFrame = 0;
        timer = 0;
        running = true;
    }
    public void stop() { running = false; }
    public void play() { running = true; }
    public boolean isRunning() { return running; }

    public void setOnFinished(Runnable r) { this.onFinished = r; }

    // Getter
    public int getCurrentFrame() { return currentFrame; }
    public int getCurrentRow() { return currentRow; }
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public Image getSpriteSheet() { return spriteSheet; }

    public int getColumns() {
        return columns;
    }

    public double getFrameDuration() {
        return frameDuration;
    }

    public int getRows() {
        return rows;
    }
}
