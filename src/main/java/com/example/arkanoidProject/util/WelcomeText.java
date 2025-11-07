package com.example.arkanoidProject.util;

import com.example.arkanoidProject.MainApp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomeText {
    private String text = "Welcome, " + MainApp.userManager.getCurrentUser().getUsername() + "!";
    private boolean finished = false;
    private double alpha = 0.8;
    private double time = 0.0;
    private double scale = 1.0;

    private double baseAngle; // góc nghiêng cơ bản
    private double tiltAmplitude = 2.5;  // độ nghiêng nhẹ
    private double tiltSpeed = 1.5;      // tốc độ nghiêng

    private double floatAmplitude = 20.0; // biên độ bay (px)
    private double floatSpeed = 1.0;      // tốc độ bay (chu kỳ giây)

    private double x, y, baseY;
    private Font font;

    public WelcomeText(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.baseY = y;
        this.baseAngle = angle;

        try {
            font = Font.loadFont(
                    getClass().getResourceAsStream("/com/example/arkanoidProject/view/fonts/PressStart2P-Regular.ttf"),
                    18
            );
        } catch (Exception e) {
            System.out.println("⚠️ Font not found, using default");
            font = Font.font("Arial", 24);
        }
    }

    public void update(double dt) {
        text = "Welcome, " + MainApp.userManager.getCurrentUser().getUsername() + "!";
        time += dt;

        // Bay lên xuống theo sóng sin
        y = baseY + Math.sin(time * floatSpeed) * floatAmplitude;

        // Alpha nhấp nháy nhẹ (lung linh)
        alpha = 0.75 + 0.25 * Math.sin(time * 1.8);
    }

    public void render(GraphicsContext gc) {
        if (finished) return;

        gc.save();
        gc.setFont(font);
        gc.setGlobalAlpha(alpha);

        Text temp = new Text(text);
        temp.setFont(font);
        double textWidth = temp.getLayoutBounds().getWidth();
        double textHeight = temp.getLayoutBounds().getHeight();

        double currentAngle = baseAngle + Math.sin(time * tiltSpeed) * tiltAmplitude;

        gc.translate(x, y);
        gc.rotate(currentAngle);
        gc.scale(scale, scale);

        double drawX = -textWidth / 2;
        double drawY = textHeight / 4;

        // Hiệu ứng phát sáng nhẹ (glow cyan)
        gc.setFill(Color.rgb(0, 255, 255, 0.05));
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) + Math.abs(j) > 0)
                    gc.fillText(text, drawX + i, drawY + j);
            }
        }

        // Màu chính – trắng lam nổi bật trong không gian
        gc.setFill(Color.web("#E0FFFF"));
        gc.fillText(text, drawX, drawY);

        gc.restore();
        gc.setGlobalAlpha(1.0);
    }
}
