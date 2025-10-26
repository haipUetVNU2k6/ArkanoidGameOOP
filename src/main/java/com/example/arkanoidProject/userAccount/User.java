package com.example.arkanoidProject.userAccount;

public class User {
    private String username;
    private int lastLevel;

    public User(String username) {
        this.username = username;
        this.lastLevel = 1; // mặc định màn 1
    }

    public String getUsername() { return username; }
    public int getLastLevel() { return lastLevel; }
    public void setLastLevel(int lastLevel) { this.lastLevel = lastLevel; }
}
