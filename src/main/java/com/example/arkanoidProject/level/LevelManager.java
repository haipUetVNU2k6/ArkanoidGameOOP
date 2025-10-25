package com.example.arkanoidProject.level;

import com.example.arkanoidProject.object.Brick;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private int currentLevel = 0;
    private List<String> levelFiles = new ArrayList<>();
    private Image brickSprite;

    public LevelManager(Image brickSprite) {
        this.brickSprite = brickSprite;

        // Danh sách file level
        levelFiles.add("/com/example/arkanoidProject/levels/level1.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level2.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level3.txt");
    }

    public List<Brick> loadCurrentLevel() {
        if (currentLevel >= levelFiles.size()) return new ArrayList<>();

        String path = levelFiles.get(currentLevel);
        int[][] layout = readLevelFromFile(path);
        List<Brick> bricks = new ArrayList<>();

        int brickWidth = 60;
        int brickHeight = 20;
        int startX = 10;
        int startY = 10;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                if (layout[row][col] == 1) {
                    Brick brick = new Brick(
                            startX + col * brickWidth,
                            startY + row * brickHeight,
                            brickWidth,
                            brickHeight,
                            brickSprite,
                            31, 18, 9, 1, 0.1
                    );
                    bricks.add(brick);
                }
            }
        }

        System.out.println("Loaded Level " + (currentLevel + 1));
        return bricks;
    }

    private int[][] readLevelFromFile(String path) {
        List<int[]> rows = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                int[] row = line.chars().map(c -> c - '0').toArray();
                rows.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new int[0][]);
    }

    public void nextLevel() {
        currentLevel++;
    }

    public boolean hasNextLevel() {
        return currentLevel < levelFiles.size();
    }

    public int getCurrentLevelIndex() {
        return currentLevel;
    }
}
