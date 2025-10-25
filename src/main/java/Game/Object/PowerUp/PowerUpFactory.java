package Game.Object.PowerUp;
import java.util.Random;

public class PowerUpFactory {
    private static final Random random = new Random();

    public static PowerUp createRandomPowerUp(double x, double y) {
        double chance = random.nextDouble();

        if (chance < 0.25)
            return new PowerUp(x, y, 20, 20, PowerUp.Type.EXPAND_PADDLE);
        else if (chance < 0.5)
            return new PowerUp(x, y, 20, 20, PowerUp.Type.EXTRA_LIFE);
        else if (chance < 0.75)
            return new PowerUp(x, y, 20, 20, PowerUp.Type.MULTI_BALL);
        else
            return new PowerUp(x, y, 20, 20, PowerUp.Type.SHOOTING_PADDLE);
    }
}
