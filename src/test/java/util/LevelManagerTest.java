package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    @Test
    void loadLevels() {
        LevelManager levelManager = new LevelManager();
        assertNotEquals(0, levelManager.getCustomLevels().size());
        assertNotEquals(0, levelManager.getOfficialLevels().size());
    }

    @Test
    void getLevelByName() {
        LevelManager levelManager = new LevelManager();
        LevelManager.Level level = levelManager.getLevelByName("test");
        assertNotNull(level);
    }
}