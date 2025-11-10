package com.example.arkanoidProject.audio;

public enum SoundType {
    // Background Music
    BACKGROUND_MUSIC("/com/example/arkanoidProject/view/sounds/background.wav", true),

    // Sound Effects
    CLICK("/com/example/arkanoidProject/view/sounds/button_click.mp3", false),
    BOUNCE("/com/example/arkanoidProject/view/sounds/bounce.mp3", false),
    WIN("/com/example/arkanoidProject/view/sounds/winLevel.wav", false),
    LOSE("/com/example/arkanoidProject/view/sounds/loseLevel.wav", false),
    BRICK_BREAK("/com/example/arkanoidProject/view/sounds/brickBreak.mp3", false),
    LOSE_BALL("/com/example/arkanoidProject/view/sounds/loseBall.wav", false),
    POWER_UP("/com/example/arkanoidProject/view/sounds/powerUp.mp3", false);

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