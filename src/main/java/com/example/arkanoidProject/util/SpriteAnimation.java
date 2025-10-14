package com.example.arkanoidProject.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class quản lý animation từ sprite sheet dạng grid.
 * Hỗ trợ update frame theo thời gian và render frame hiện tại lên canvas.
 */
public class SpriteAnimation {
    private final Image spriteSheet;
    private final int frameWidth, frameHeight;
    private final int columns, rows;
    private final int totalFrames;

    private int currentFrame = 0;
    private double frameDuration; // thời gian 1 frame (giây)
    private double timer = 0;
    private boolean loop = true;
    private boolean running = true;

    /**
     * @param spriteSheet ảnh sprite sheet chứa nhiều frame theo dạng lưới
     * @param frameWidth  chiều rộng 1 frame
     * @param frameHeight chiều cao 1 frame
     * @param columns     số cột frame trên sprite sheet
     * @param rows        số hàng frame trên sprite sheet
     * @param frameDuration thời gian hiển thị 1 frame (giây)
     */
    public SpriteAnimation(Image spriteSheet, int frameWidth, int frameHeight,
                           int columns, int rows, double frameDuration) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.columns = columns;
        this.rows = rows;
        this.totalFrames = columns * rows;
        this.frameDuration = frameDuration;
    }

    /**
     * Cập nhật animation theo thời gian delta (giây).
     */
    public void update(double dt) {
        if (!running) return;

        timer += dt;
        if (timer >= frameDuration) {
            timer -= frameDuration;
            currentFrame++;
            if (currentFrame >= totalFrames) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = totalFrames - 1;
                    running = false;
                }
            }
        }
    }

    /**
     * Render frame hiện tại lên GraphicsContext tại vị trí (x, y) với scale.
     *
     * @param gc     GraphicsContext để vẽ
     * @param x      vị trí x trên canvas
     * @param y      vị trí y trên canvas
     * @param scaleX tỉ lệ phóng theo chiều ngang
     * @param scaleY tỉ lệ phóng theo chiều dọc
     */
    public void render(GraphicsContext gc, double x, double y, double scaleX, double scaleY) {
        int frameX = (currentFrame % columns) * frameWidth;
        int frameY = (currentFrame / columns) * frameHeight;

        gc.drawImage(spriteSheet,
                frameX, frameY,
                frameWidth, frameHeight,
                x, y,
                frameWidth * scaleX, frameHeight * scaleY);
    }

    // --- Các method tiện ích ---

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void reset() {
        currentFrame = 0;
        timer = 0;
        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int frame) {
        if (frame >= 0 && frame < totalFrames) {
            this.currentFrame = frame;
            this.timer = 0;
        }
    }

    public double getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(double frameDuration) {
        this.frameDuration = frameDuration;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public Image getSpriteSheet() {
        return spriteSheet;
    }
}
