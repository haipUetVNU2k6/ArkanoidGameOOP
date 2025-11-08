package com.example.arkanoidProject.state_controller.controller;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.state.HighScoreState;
import com.example.arkanoidProject.userAccount.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class HighScoreCtrl {
    @FXML
    private TableView<UserScore> scoreTable;

    @FXML
    private TableColumn<UserScore, String> usernameColumn;

    @FXML
    private TableColumn<UserScore, Number> level1Column;

    @FXML
    private TableColumn<UserScore, Number> level2Column;

    @FXML
    private TableColumn<UserScore, Number> level3Column;

    @FXML
    private TableColumn<UserScore, Number> level4Column;

    @FXML
    private TableColumn<UserScore, Number> level5Column;

    @FXML
    private TableColumn<UserScore, Number> level6Column;

    private HighScoreState state;
    private ObservableList<UserScore> scoreData;

    public void setState(HighScoreState state) {
        this.state = state;
    }

    @FXML
    public void initialize() {
        // Setup columns
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());
        level1Column.setCellValueFactory(data -> data.getValue().level1Property());
        level2Column.setCellValueFactory(data -> data.getValue().level2Property());
        level3Column.setCellValueFactory(data -> data.getValue().level3Property());
        level4Column.setCellValueFactory(data -> data.getValue().level4Property());
        level5Column.setCellValueFactory(data -> data.getValue().level5Property());
        level6Column.setCellValueFactory(data -> data.getValue().level6Property());

        // Set cell factory để hiển thị thời gian
        setupCellFactory(level1Column);
        setupCellFactory(level2Column);
        setupCellFactory(level3Column);
        setupCellFactory(level4Column);
        setupCellFactory(level5Column);
        setupCellFactory(level6Column);

        // Enable sorting
        scoreTable.setSortPolicy(tv -> {
            FXCollections.sort(tv.getItems(), tv.getComparator());
            return true;
        });
    }

    private void setupCellFactory(TableColumn<UserScore, Number> column) {
        column.setCellFactory(col -> new javafx.scene.control.TableCell<UserScore, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    int value = item.intValue();

                    if (value == -1) {
                        setText("N/A");
                        setStyle("-fx-text-fill: #666666;");
                    } else {
                        // Format time as seconds (e.g., 45s, 120s)
                        setText(value + "s");

                        // Style dựa trên thời gian (càng thấp càng tốt)
                        if (value <= 30) {
                            // Excellent: <= 30 seconds (Green)
                            setStyle("-fx-text-fill: #00ff00; -fx-font-weight: bold;");
                        } else if (value <= 60) {
                            // Good: 31-60 seconds (Yellow)
                            setStyle("-fx-text-fill: #ffff00;");
                        } else if (value <= 120) {
                            // Fair: 61-120 seconds (Orange)
                            setStyle("-fx-text-fill: #ff9900;");
                        } else {
                            // Poor: > 120 seconds (Red)
                            setStyle("-fx-text-fill: #ff5555;");
                        }
                    }
                }
            }
        });

        // Custom comparator: thời gian thấp hơn = tốt hơn = lên đầu
        column.setComparator((num1, num2) -> {
            int val1 = num1.intValue();
            int val2 = num2.intValue();

            // -1 (chưa chơi) luôn ở cuối
            if (val1 == -1 && val2 == -1) return 0;
            if (val1 == -1) return 1;  // val1 xuống cuối
            if (val2 == -1) return -1; // val2 xuống cuối

            // Sort tăng dần (thời gian thấp = tốt hơn = lên trên)
            return Integer.compare(val1, val2);
        });
    }

    public void loadHighScores() {
        scoreData = FXCollections.observableArrayList();
        List<User> users = MainApp.userManager.getUsers();

        for (User user : users) {
            int[] levels = user.getLevelResults();

            // Kiểm tra xem user có ít nhất 1 level đã chơi không
            boolean hasPlayedAnyLevel = false;
            for (int level : levels) {
                if (level != -1) {
                    hasPlayedAnyLevel = true;
                    break;
                }
            }

            // Chỉ thêm user đã chơi ít nhất 1 level
            if (hasPlayedAnyLevel) {
                UserScore userScore = new UserScore(
                        user.getUsername(),
                        levels.length > 0 ? levels[0] : -1,
                        levels.length > 1 ? levels[1] : -1,
                        levels.length > 2 ? levels[2] : -1,
                        levels.length > 3 ? levels[3] : -1,
                        levels.length > 4 ? levels[4] : -1,
                        levels.length > 5 ? levels[5] : -1
                );
                scoreData.add(userScore);
            }
        }

        scoreTable.setItems(scoreData);

        // Default sort by Level 1 (thời gian thấp nhất lên đầu)
        scoreTable.getSortOrder().add(level1Column);
    }

    @FXML
    private void onBackToMenu() {
        state.backToMenu();
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            onBackToMenu();
        }
    }

    // Inner class để represent một row trong table
    public static class UserScore implements Comparable<UserScore> {
        private final SimpleStringProperty username;
        private final SimpleIntegerProperty level1;
        private final SimpleIntegerProperty level2;
        private final SimpleIntegerProperty level3;
        private final SimpleIntegerProperty level4;
        private final SimpleIntegerProperty level5;
        private final SimpleIntegerProperty level6;

        public UserScore(String username, int l1, int l2, int l3, int l4, int l5, int l6) {
            this.username = new SimpleStringProperty(username);
            this.level1 = new SimpleIntegerProperty(l1);
            this.level2 = new SimpleIntegerProperty(l2);
            this.level3 = new SimpleIntegerProperty(l3);
            this.level4 = new SimpleIntegerProperty(l4);
            this.level5 = new SimpleIntegerProperty(l5);
            this.level6 = new SimpleIntegerProperty(l6);
        }

        public SimpleStringProperty usernameProperty() { return username; }
        public SimpleIntegerProperty level1Property() { return level1; }
        public SimpleIntegerProperty level2Property() { return level2; }
        public SimpleIntegerProperty level3Property() { return level3; }
        public SimpleIntegerProperty level4Property() { return level4; }
        public SimpleIntegerProperty level5Property() { return level5; }
        public SimpleIntegerProperty level6Property() { return level6; }

        @Override
        public int compareTo(UserScore other) {
            // Default comparison by username
            return this.username.get().compareTo(other.username.get());
        }
    }
}