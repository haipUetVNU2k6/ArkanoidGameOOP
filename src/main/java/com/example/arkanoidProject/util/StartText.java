package com.example.arkanoidProject.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartText {
    private final String text = "PRESS SPACE TO START";
    private boolean finished = false;
    private double alpha = 1.0;
    private double time = 0.0;
    private double scale = 1.0;

    private double x, y;
    private Font font;

    public StartText(double x, double y) {
        this.x = x;
        this.y = y;

        try {
            font = Font.loadFont(
                    getClass().getResourceAsStream("/com/example/arkanoidProject/view/fonts/PressStart2P-Regular.ttf"),
                    20
            );
        } catch (Exception e) {
            System.out.println("⚠️ Font not found, using default");
            font = Font.font("Arial", 24);
        }
    }

    public void update(double dt) {
        if (finished) return;
        time += dt;
        alpha = 0.6 + 0.4 * Math.sin(time * 3);  // mờ dần hiện dần
        scale = 1.0 + 0.05 * Math.sin(time * 2); // phồng nhẹ
    }

    public void render(GraphicsContext gc) {
        if (finished) return;

        gc.save();
        gc.setGlobalAlpha(alpha);
        gc.setFont(font);

        // đo kích thước chữ
        Text temp = new Text(text);
        temp.setFont(font);
        double textWidth = temp.getLayoutBounds().getWidth();
        double textHeight = temp.getLayoutBounds().getHeight();

        // scale quanh tâm
        gc.translate(x, y);
        gc.scale(scale, scale);
        gc.translate(-x, -y);

        double drawX = x - textWidth / 2;
        double drawY = y + textHeight / 4;

        // bóng đổ
        gc.setFill(Color.BLACK);
        gc.fillText(text, drawX + 2, drawY + 2);

        // màu chính
        gc.setFill(Color.web("#00FFFF"));
        gc.fillText(text, drawX, drawY);

        gc.restore();
        gc.setGlobalAlpha(1.0);
    }

    public void hide() {
        finished = true;
    }
}
