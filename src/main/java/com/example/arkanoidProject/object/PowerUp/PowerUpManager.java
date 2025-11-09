package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Ball;
import com.example.arkanoidProject.object.BallManager;
import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;

import java.util.concurrent.*;

public class PowerUpManager {

    private final ConcurrentLinkedQueue<PowerUp> activePowerUps =
            new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);

    public void applyPowerUp(PowerUp powerUp, Paddle paddle, BallManager ballManager) {
        // Áp dụng effect ngay lập tức

       // activePowerUps.add(powerUp);
        powerUp.applyEffect(paddle,ballManager);

        // Schedule việc remove effect sau duration giây
        scheduler.schedule(() -> {
            powerUp.removeEffect(paddle,ballManager);
           // activePowerUps.remove(powerUp);
        }, powerUp.getDuration(), TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public void onBrickDestroyed(Brick brick) {
        // 30% chance drop power-up
        if (Math.random() < 0.3) {
            // danh số tham số nên là (x,y,w,h) hay là (x,y)
            PowerUp powerUp = PowerUpFactory.createRandomPowerUp(
                    brick.getX(), brick.getY(), Config.diameterPowerUp, Config.diameterPowerUp
            );
            if(powerUp == null) System.out.println("Error");
            activePowerUps.add(powerUp);
        }
    }

    public ConcurrentLinkedQueue<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }
}
