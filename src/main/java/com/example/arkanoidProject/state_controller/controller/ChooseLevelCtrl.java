package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.state.ChooseLevelState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChooseLevelCtrl {

    @FXML private Button level1Btn;
    @FXML private Button level2Btn;
    @FXML private Button level3Btn;
    @FXML private Button level4Btn;
    @FXML private Button level5Btn;
    @FXML private Button level6Btn;
    @FXML private Button backBtn;

    private int unlockedLevel;

    // Hàm init được gọi từ ChooseLevelState khi hiển thị FXML
    public void init(ChooseLevelState state) {
        // Lấy level hiện tại từ User
        unlockedLevel = MainApp.userManager.getCurrentUser().getLastLevel();
        setupButtons();
        setupBackButton();
    }

    // Gán style và action cho từng level
    private void setupButtons() {
        setupButton(level1Btn, 1);
        setupButton(level2Btn, 2);
        setupButton(level3Btn, 3);
        setupButton(level4Btn, 4);
        setupButton(level5Btn, 5);
        setupButton(level6Btn, 6);
    }

    private void setupButton(Button btn, int level) {
        // Xóa style cũ trước để tránh cộng dồn khi refresh
        btn.getStyleClass().removeAll("level-unlocked", "level-locked", "button");
        btn.getStyleClass().add("button");

        if (level <= unlockedLevel) {
            btn.getStyleClass().add("level-unlocked");
            btn.setDisable(false);
            btn.setOnAction(e -> onSelectLevel(level));
        } else {
            btn.getStyleClass().add("level-locked");
            btn.setDisable(true);
        }
    }

    private void setupBackButton() {
        // Nên thêm cả class "button" để áp dụng style chung
        if (!backBtn.getStyleClass().contains("button"))
            backBtn.getStyleClass().add("button");

        if (!backBtn.getStyleClass().contains("back-btn"))
            backBtn.getStyleClass().add("back-btn");

        backBtn.setOnAction(e -> MainApp.stateStack.pop());
    }

    private void onSelectLevel(int level) {
        System.out.println("Selected Level: " + level);
        // Gọi phương thức trong MainApp để đổi sang PlayState kèm level
//        MainApp.playState.setLevel(level);
        MainApp.stateStack.push(new PlayState());
    }
}
