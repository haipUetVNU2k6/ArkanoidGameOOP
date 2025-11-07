package com.example.arkanoidProject.levels;

import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.BrickSkin;
import com.example.arkanoidProject.util.Config;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private final List<String> levelFiles = new ArrayList<>();
    Image brick1Image;
    Image brick2Image;
    Image brick3Image;

    public LevelManager() {
        brick1Image = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick/brick1.png").toExternalForm());
        brick2Image = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick/brick2.png").toExternalForm());
        brick3Image = new Image(getClass().getResource("/com/example/arkanoidProject/view/images/brick/brick3.png").toExternalForm());


        // danh sách file level
        levelFiles.add("/com/example/arkanoidProject/levels/level1.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level2.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level3.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level4.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level5.txt");
        levelFiles.add("/com/example/arkanoidProject/levels/level6.txt");
    }
    /** Load màn theo chỉ số levelToLoad */
    public List<Brick> loadLevel(int levelToLoad) {
        if (levelToLoad <= 0 || levelToLoad > levelFiles.size()) return new ArrayList<>();

        String path = levelFiles.get(--levelToLoad);
        int[][] layout = readLevelFromFile(path);

        List<Brick> bricks = new ArrayList<>();
        int brickWidth = Config.brickWidth;
        int brickHeight = Config.brickHeight;
        int startX = 0;
        int startY = Config.brickHeight; // để trống dòng đầu để ghi lives, time

        for (int row = 0; row < Math.min(Config.maxBrickInOneRow(), layout.length); row++) {
            for (int col = 0; col < Math.min(Config.maxBrickInOneCol(), layout[row].length); col++) {
                if (layout[row][col] == 1) {
                    Brick brick = new Brick(
                            startX + col * brickWidth,
                            startY + row * brickHeight,
                            brickWidth,
                            brickHeight,
                            BrickSkin.BRICK1, 1
                    );
                    bricks.add(brick);
                } else if (layout[row][col] == 2) {
                    Brick brick = new Brick(
                            startX + col * brickWidth,
                            startY + row * brickHeight,
                            brickWidth,
                            brickHeight,
                            BrickSkin.BRICK2, 2
                    );
                    bricks.add(brick);
                } else if (layout[row][col] == 3) {
                    Brick brick = new Brick(
                            startX + col * brickWidth,
                            startY + row * brickHeight,
                            brickWidth,
                            brickHeight,
                            BrickSkin.BRICK3, 3
                    );
                    bricks.add(brick);
                }
            }
        }
        return bricks;
    }

    /** Đọc file level thành ma trận 0,1 */
    private int[][] readLevelFromFile(String path) {
        List<int[]> rows = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                int[] row = line.chars()
                        .filter(c -> c >= '0' && c <= '3') // đọc số 0–3
                        .map(c -> c - '0')
                        .toArray();
                rows.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new int[0][]);
    }
}
