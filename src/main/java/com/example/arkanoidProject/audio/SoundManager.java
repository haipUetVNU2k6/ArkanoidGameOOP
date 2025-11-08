package com.example.arkanoidProject.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple SoundManager - Quản lý âm thanh trong game
 */
public class SoundManager {
    private static SoundManager instance;

    private MediaPlayer backgroundMusicPlayer;
    private Map<SoundType, Media> soundCache;

    private double volume = 0.7;
    private boolean isMuted = false;

    private SoundManager() {
        soundCache = new HashMap<>();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Khởi tạo - gọi 1 lần duy nhất khi start app
     */
    public void initialize() {
        try {
            // Preload tất cả sounds
            for (SoundType soundType : SoundType.values()) {
                String path = getClass().getResource(soundType.getPath()).toExternalForm();
                soundCache.put(soundType, new Media(path));
            }

            // Setup background music
            Media bgMusic = soundCache.get(SoundType.BACKGROUND_MUSIC);
            if (bgMusic != null) {
                backgroundMusicPlayer = new MediaPlayer(bgMusic);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.setVolume(volume);
            }

            System.out.println("SoundManager initialized!");
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

    /**
     * Play sound effect
     */
    public void play(SoundType soundType) {
        if (isMuted || soundType.isMusic()) return;

        try {
            Media sound = soundCache.get(soundType);
            if (sound != null) {
                MediaPlayer player = new MediaPlayer(sound);
                player.setVolume(volume);
                player.play();
                player.setOnEndOfMedia(() -> player.dispose());
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + soundType);
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusicPlayer != null && !isMuted) {
            backgroundMusicPlayer.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    /**
     * Set volume (0.0 - 1.0)
     */
    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(this.volume);
        }
    }

    /**
     * Toggle mute
     */
    public void toggleMute() {
        isMuted = !isMuted;
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setMute(isMuted);
        }
    }

    /**
     * Cleanup khi thoát
     */
    public void dispose() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.dispose();
        }
        soundCache.clear();
    }

    // Getters
    public double getVolume() {
        return volume;
    }

    public boolean isMuted() {
        return isMuted;
    }
}