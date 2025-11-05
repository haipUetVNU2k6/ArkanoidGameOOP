package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.object.Brick;
import com.example.arkanoidProject.object.Paddle;
import com.example.arkanoidProject.util.Config;

import java.util.concurrent.*;

public class PowerUpManager {

    private final ConcurrentLinkedQueue<PowerUp> activePowerUps =
            new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);

    public void applyPowerUp(PowerUp powerUp, Paddle paddle) {
        // Áp dụng effect ngay lập tức

       // activePowerUps.add(powerUp);
        powerUp.applyEffect(paddle);

        // Schedule việc remove effect sau duration giây
        scheduler.schedule(() -> {
            powerUp.removeEffect(paddle);
            activePowerUps.remove(powerUp);
        }, powerUp.getDuration(), TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public void onBrickDestroyed(Brick brick) {
        // 30% chance drop power-up
        if (Math.random() < 1.0) {
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
