package com.example.arkanoidProject.userAccount;

public class User {
    private String username;
    private int lastLevel;
    private int[] levelResults = new int[6]; // kết quả 6 màn

    public User(String username) {
        this.username = username;
        this.lastLevel = 1; // mặc định màn 1
        for (int i = 0; i < levelResults.length; i++) {
            levelResults[i] = -1; // mặc định màn chưa mở = -1
        }
    }

    public String getUsername() { return username; }
    public int getLastLevel() { return lastLevel; }
    public void setLastLevel(int lastLevel) { this.lastLevel = lastLevel; }

    // 🔹 Getter/Setter toàn mảng — bắt buộc để Gson lưu
    public int[] getLevelResults() {
        return levelResults;
    }

    public void setLevelResults(int[] levelResults) {
        this.levelResults = levelResults;
    }

    public int getLevelResult(int level) {
        return levelResults[level - 1]; // level 1 → index 0
    }

    public void setLevelResult(int level, int result) {
        levelResults[level - 1] = result;
    }
}
