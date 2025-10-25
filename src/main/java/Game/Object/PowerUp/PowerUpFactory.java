package Game.Object.PowerUp;

import java.util.Random;

public class PowerUpFactory {
    private static final Random random = new Random();

    public static PowerUp createRandomPowerUp(double x, double y) {
        if (random.nextDouble() < 1.0) {
            return new PowerUp(x, y, 20, 20, PowerUp.Type.EXPAND_PADDLE);
        }
        return null;
    }
    public static void main(String[] args) {

    }
}
