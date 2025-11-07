package com.example.arkanoidProject.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HealthText {
    private String text = "";
    private boolean finished = false;

    private double x, y;
    private Font font;

    private double elapsedTime = 0; // thêm biến thời gian

    public HealthText(double x, double y) {
        this.x = x;
        this.y = y;

        try {
            font = Font.loadFont(
                    getClass().getResourceAsStream("/com/example/arkanoidProject/view/fonts/PressStart2P-Regular.ttf"),
                    15
            );
        } catch (Exception e) {
            System.out.println("⚠️ Font not found, using default");
            font = Font.font("Arial", 24);
        }
    }

    // dt: thời gian trôi qua từ frame trước (giây), lives: số mạng
    public void update(int lives, int seconds) {

        text = "Lives: " + lives + "  Time: " + seconds + "s"; // gộp cả lives và thời gian
    }

    public void render(GraphicsContext gc) {
        if (finished) return;

        gc.save();
        gc.setFont(font);

        // đo kích thước chữ
        Text temp = new Text(text);
        temp.setFont(font);
        double textWidth = temp.getLayoutBounds().getWidth();
        double textHeight = temp.getLayoutBounds().getHeight();

        double drawX = x;
        double drawY = y + textHeight;

        // bóng đổ
        gc.setFill(Color.BLACK);
        gc.fillText(text, drawX + 2, drawY + 2);

        // màu chính
        gc.setFill(Color.web("#00FFFF"));
        gc.fillText(text, drawX, drawY);

        gc.restore();
    }

    public void hide() {
        finished = true;
    }
}
