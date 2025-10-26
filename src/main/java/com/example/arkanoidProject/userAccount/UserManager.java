package com.example.arkanoidProject.userAccount;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String USER_FILE = "users.json";

    private List<User> users = new ArrayList<>();
    private User currentUser;
    private String lastUserUsername; // lưu username lần cuối chơi

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public UserManager() {
        loadUsers();
    }

    /** Load danh sách users và user lần cuối */
    public void loadUsers() {
        try (Reader reader = new FileReader(USER_FILE)) {
            Type type = new TypeToken<UserData>(){}.getType();
            UserData data = gson.fromJson(reader, type);
            if (data != null) {
                users = data.users != null ? data.users : new ArrayList<>();
                lastUserUsername = data.lastUserUsername;

                // set currentUser nếu lastUserUsername tồn tại
                if (lastUserUsername != null) {
                    currentUser = users.stream()
                            .filter(u -> u.getUsername().equals(lastUserUsername))
                            .findFirst()
                            .orElse(null);
                }
            }
        } catch (Exception e) {
            users = new ArrayList<>();
            currentUser = null;
            lastUserUsername = null;
        }
    }

    /** Save users + lastUser */
    public void saveUsers() {
        try (Writer writer = new FileWriter(USER_FILE)) {
            UserData data = new UserData();
            data.users = users;
            data.lastUserUsername = currentUser != null ? currentUser.getUsername() : null;
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        setCurrentUser(user); // tạo xong tự set current
        saveUsers();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.lastUserUsername = user != null ? user.getUsername() : null;
        saveUsers(); // lưu luôn khi đổi user
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /** lớp dữ liệu để lưu JSON */
    private static class UserData {
        List<User> users;
        String lastUserUsername;
    }
}
