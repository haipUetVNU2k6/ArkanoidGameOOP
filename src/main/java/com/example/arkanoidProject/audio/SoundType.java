package com.example.arkanoidProject.audio;

/**
 * Enum chứa tất cả các loại âm thanh trong game
 * Dễ dàng thêm/sửa/xóa sound effects
 */
public enum SoundType {
    // Background Music
    BACKGROUND_MUSIC("/com/example/arkanoidProject/view/sounds/background.wav", true),

    // Sound Effects
    CLICK("/com/example/arkanoidProject/view/sounds/click.wav", false),
    BOUNCE("/com/example/arkanoidProject/view/sounds/bounce.wav", false),
    WIN("/com/example/arkanoidProject/view/sounds/win.wav", false),
    LOSE("/com/example/arkanoidProject/view/sounds/lose.wav", false),
    BRICK_BREAK("/com/example/arkanoidProject/view/sounds/brick_break.wav", false),
    LOSE_BALL("/com/example/arkanoidProject/view/sounds/lose_ball.wav", false);

    private final String fileName;
    private final boolean isMusic; // true = nhạc nền, false = sound effect

    SoundType(String fileName, boolean isMusic) {
        this.fileName = fileName;
        this.isMusic = isMusic;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public String getPath() {
        return fileName;
    }
}