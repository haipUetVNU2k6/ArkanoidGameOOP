package com.example.arkanoidProject.object.PowerUp;

import com.example.arkanoidProject.util.Config;

import java.util.Random;

public abstract class PowerUpFactory {
    private static final Random random = new Random();

    // Tạo power-up ngẫu nhiên
    public static PowerUp createRandomPowerUp(double x, double y, double width, double height) {
        Config.PowerUpType[] types = Config.PowerUpType.values();
        Config.PowerUpType randomType = types[random.nextInt(types.length)];
        return createPowerUp(randomType, x, y, width, height);
    }
    // Tạo power-up theo type cụ thể
    public static PowerUp createPowerUp(Config.PowerUpType type, double x, double y, double width, double height) {
        switch(type) {
            case EXPAND_PADDLE:
                return new ExpandPaddlePowerUp(x, y, width, height, 10); // 10 giây
            case SHRINK_PADDLE:
                return new ShrinkPaddlePowerUp(x, y, width, height, 8);
            case MULTI_BALL:
                return new MultiBallPowerUp(x, y, width, height,100);
            case EXTRA_TIME:
                return new ExtraTimePowerUp(x, y ,width , height, 10);
            case EXTRA_LIVES:
                return new ExtraLivesPowerUp(x, y, width, height , 10);
            case MAGNET:
                return new MagnetPowerUp(x, y, width, height,50);
            default:
                return null;
        }
    }
}
