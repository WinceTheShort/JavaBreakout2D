package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrickTest {

    @Test
    void damage() {
        Brick b = new Brick();

        b.setActiveSprite(4);
        b.setAlive(true);
        assertEquals(4, b.getActiveSprite());
        assertTrue(b.isAlive());

        int score = b.damage();
        assertEquals(10, score);
        assertEquals(3, b.getActiveSprite());

        score = b.damage();
        assertEquals(20, score);
        assertEquals(2, b.getActiveSprite());

        score = b.damage();
        assertEquals(40, score);
        assertEquals(1, b.getActiveSprite());

        score = b.damage();
        assertEquals(100, score);
        assertEquals(0, b.getActiveSprite());
        assertFalse(b.isAlive);
    }
}