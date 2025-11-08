package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.audio.SoundManager;
import com.example.arkanoidProject.audio.SoundType;
import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.util.Config;
import com.example.arkanoidProject.util.ParticleCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class SettingsCtrl {

    @FXML
    private StackPane root;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label volumeLabel;

    @FXML
    private Button btnMute;

    @FXML
    private Button btnBack;

    @FXML
    public void initialize() {
        // Set giá trị ban đầu cho slider
        SoundManager soundManager = SoundManager.getInstance();
        volumeSlider.setValue(soundManager.getVolume() * 100);
        updateVolumeLabel(soundManager.getVolume() * 100);
        updateMuteButton(soundManager.isMuted());

        // Lắng nghe thay đổi volume
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue() / 100.0;
            soundManager.setVolume(volume);
            updateVolumeLabel(newVal.doubleValue());

            // Nếu đang mute, tự động unmute khi kéo slider
            if (soundManager.isMuted()) {
                soundManager.toggleMute();
                updateMuteButton(false);
            }
        });
    }

    @FXML
    private void onMuteToggle(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        SoundManager soundManager = SoundManager.getInstance();
        soundManager.toggleMute();
        updateMuteButton(soundManager.isMuted());
    }

    @FXML
    private void onBack(ActionEvent event) {
        SoundManager.getInstance().play(SoundType.CLICK);

        // Quay lại menu
        MainApp.stateStack.pop();
    }

    private void updateVolumeLabel(double volume) {
        volumeLabel.setText(String.format("Volume: %d%%", (int) volume));
    }

    private void updateMuteButton(boolean isMuted) {
        if (isMuted) {
            btnMute.setText("🔇 Unmute");
        } else {
            btnMute.setText("🔊 Mute");
        }
    }
}