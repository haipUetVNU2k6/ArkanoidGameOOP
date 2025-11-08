package com.example.arkanoidProject.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

public class SoundManager {

    private static final Map<SoundEffect, AudioClip> soundCache = new HashMap<>();
    private static MediaPlayer gameLoopPlayer;
// load âm thanh
    static {
        for (SoundEffect effect : SoundEffect.values()) {
            try {
                URL resource = SoundManager.class.getResource(effect.getFilePath());

                if (resource == null) {

                    System.err.println("error" + effect.getFilePath());
                    continue;
                }

                String urlString = resource.toExternalForm();

                if (effect == SoundEffect.GAME_LOOP) {
                    Media media =  new Media(urlString);
                    gameLoopPlayer = new MediaPlayer(media);
                    gameLoopPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                } else {
                    AudioClip clip = new AudioClip(urlString);
                    soundCache.put(effect, clip);
                }

            } catch (Exception e) {
                System.err.println("error " + effect + " (" + effect.getFilePath() + ")");
                e.printStackTrace();
            }
        }
    }

    public static void playSound(SoundEffect effect) {
        AudioClip clip = soundCache.get(effect);
        if (clip != null) {
            clip.play();
        }
    }

// lặp nhạc nền
    public static void loopSound(SoundEffect effect) {
        if (effect == SoundEffect.GAME_LOOP && gameLoopPlayer != null) {
            gameLoopPlayer.play();
        }
    }


    public static void stopSound(SoundEffect effect) {
        if (effect == SoundEffect.GAME_LOOP && gameLoopPlayer != null) {
            gameLoopPlayer.stop();
        } else {
            AudioClip clip = soundCache.get(effect);
            if (clip != null && clip.isPlaying()) {
                clip.stop();
            }
        }
    }
}
