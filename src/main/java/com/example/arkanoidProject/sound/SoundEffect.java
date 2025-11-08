package com.example.arkanoidProject.sound;
// cac loai sound
public enum SoundEffect {
    BRICK_DESTROY("/com/example/arkanoidProject/sound/brick_destroy.wav"),
    BALL_BRICK("/com/example/arkanoidProject/sound/ball_brick.wav"),
    BALL_PADDLE("/com/example/arkanoidProject/sound/ball_paddle.wav"),
    BALL_LOST("/com/example/arkanoidProject/sound/ball_lost.wav"),
    BUTTON_CLICK("/com/example/arkanoidProject/sound/button_click.wav"),
    GAME_WIN("/com/example/arkanoidProject/sound/game_win.wav"),
    GAME_LOSE("/com/example/arkanoidProject/sound/game_lose.wav"),
    GAME_LOOP("/com/example/arkanoidProject/sound/game_loop.mp3");
    private final String filePath;

    SoundEffect(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
