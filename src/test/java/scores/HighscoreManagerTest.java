package scores;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighscoreManagerTest {

    @Test
    void load_getHighscores() {
        HighscoreManager manager = new HighscoreManager();
        assertNotEquals(0, manager.getHighscores().size());

    }
}