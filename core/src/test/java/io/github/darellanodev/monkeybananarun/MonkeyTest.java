package io.github.darellanodev.monkeybananarun;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonkeyTest {
    @Test
    void movingRightIncreasesX() {
        Monkey monkey = new Monkey();
        float initialX = monkey.getX();
        monkey.applyMovement(1f, 1);
        assertTrue(monkey.getX() > initialX);
    }

    @Test
    void noMovementKeepX() {
        Monkey monkey = new Monkey();
        float initialX = monkey.getX();
        monkey.applyMovement(1f, 0);
        assertEquals(monkey.getX(), initialX);
    }

    @Test
    void movingLeftFacesLeft() {
        Monkey monkey = new Monkey();
        monkey.applyMovement(1f, -1);
        assertFalse(monkey.isFacingRight());
    }
}
