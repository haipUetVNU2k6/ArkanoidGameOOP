package com.example.arkanoidProject.object;

import com.example.arkanoidProject.util.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BallManager {
    private final List<Ball> balls = new ArrayList<>();
    private final Image ballSprite;

    public BallManager(Image ballSprite) {
        this.ballSprite = ballSprite;
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void reset() {
        balls.clear();
    }

    public List<Ball> getBalls() {
        return balls;
    }

    /** Cập nhật tất cả bóng */
    public void updateAll(double dt) {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            ball.update(dt);

            // Nếu bóng rơi khỏi màn hình → remove
            if (ball.getY() > Config.getScreenHeight()) {
                it.remove();
            }
        }
    }

    /** Vẽ tất cả bóng */
    public void renderAll(GraphicsContext gc) {
        for (Ball ball : balls) {
            ball.render(gc);
        }
    }

    /** Sinh thêm bóng (cho MultiBallPowerUp) */
    public void spawnExtraBalls(Ball sourceBall) {
        Ball ball1 = createClonedBall(sourceBall);
        Ball ball2 = createClonedBall(sourceBall);

        // Chỉnh hướng bay lệch nhau để tách quỹ đạo
        ball1.setDx(sourceBall.getDx() * 0.8);
        ball1.setDy(-Math.abs(sourceBall.getDy()));
        ball2.setDx(-sourceBall.getDx() * 0.8);
        ball2.setDy(-Math.abs(sourceBall.getDy()));
        ball1.setHeld(false);
        ball2.setHeld(false);
        balls.add(ball1);
        balls.add(ball2);
    }

    /** Tạo bản sao của bóng hiện tại */
    private Ball createClonedBall(Ball source) {
        Ball clone = new Ball(source.getX(), source.getY(),
                Config.ballWidth, Config.ballHeight,
                ballSprite, 10, 1, 880, 512, 0.1,
                Config.ballHitBoxOffsetX, Config.ballHitBoxOffsetY,
                Config.ballHitBoxW, Config.ballHitBoxH);
        return clone;
    }
}
