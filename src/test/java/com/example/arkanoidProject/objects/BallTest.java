package com.example.arkanoidProject.objects;

import com.example.arkanoidProject.object.Ball;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void testBallInitialState() {
        Ball ball = new Ball(100, 200, 20, 20, null, 0, 0, 0, 0, 0, 0, 0, 20, 20);

        assertTrue(ball.isHeld());
        assertEquals(100, ball.getDx()); // nếu startBallDx = 100
        assertEquals(100, ball.getDy()); // nếu startBallDy = 100
    }

    @Test
    void testStopHolding() {
        Ball ball = new Ball(100, 200, 20, 20, null, 0, 0, 0, 0, 0, 0, 0, 20, 20);

        ball.stopHolding();
        assertFalse(ball.isHeld());
    }
}
