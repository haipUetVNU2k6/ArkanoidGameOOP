package com.example.arkanoidProject.levels;

import com.example.arkanoidProject.object.Brick;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private int currentLevel = 0; // index màn hiện tại
    private final List<String> levelFiles = new ArrayList<>();
    private final Image brickImage;

    public LevelManager(Image brickImage) {
        this.brickImage = brickImage;

        // danh sách file level
        levelFiles.add("/com/example/arkanoidProject/levels/level1.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level2.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level3.txt");
    }

    /** Load màn hiện tại */
    public List<Brick> loadCurrentLevel() {
        if (currentLevel < 0 || currentLevel >= levelFiles.size()) return new ArrayList<>();
        return loadLevel(currentLevel);
    }

    /** Load màn theo chỉ số levelIndex */
    public List<Brick> loadLevel(int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levelFiles.size()) return new ArrayList<>();
        currentLevel = levelIndex;

        String path = levelFiles.get(currentLevel);
        int[][] layout = readLevelFromFile(path);

        List<Brick> bricks = new ArrayList<>();
        int brickWidth = 60 * 2;
        int brickHeight = 20 * 2;
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
                            brickImage
                    );
                    bricks.add(brick);
                }
            }
        }

        System.out.println("Loaded Level " + (currentLevel + 1));
        return bricks;
    }

    /** Chuyển sang màn tiếp theo */
    public void nextLevel() {
        currentLevel++;
    }

    /** Kiểm tra còn màn tiếp theo không */
    public boolean hasNextLevel() {
        return currentLevel < levelFiles.size();
    }

    /** Lấy index màn hiện tại */
    public int getCurrentLevelIndex() {
        return currentLevel;
    }

    /** Đọc file level thành ma trận 0,1 */
    private int[][] readLevelFromFile(String path) {
        List<int[]> rows = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                int[] row = line.chars()
                        .filter(c -> c == '0' || c == '1') // lọc ký tự hợp lệ
                        .map(c -> c - '0')
                        .toArray();
                rows.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new int[0][]);
    }

    /** Reset về màn đầu */
    public void reset() {
        currentLevel = 0;
    }

    /** Tổng số màn chơi */
    public int getTotalLevels() {
        return levelFiles.size();
    }
}
