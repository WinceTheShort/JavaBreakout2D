package level_select;

import org.junit.jupiter.api.Test;
import util.LevelManager;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class LevelCheckboxTest {

    @Test
    void actionPerformed() {
        LevelManager.Level level = new LevelManager.Level(new File("src/levels/custom/dog.save"));

        LevelCheckbox levelCheckbox = new LevelCheckbox(level,0);
        assertTrue(levelCheckbox.isSelectable());
        assertFalse(levelCheckbox.isSelected());

        levelCheckbox.actionPerformed(new ActionEvent(level, 0, levelCheckbox.getLevelName()));
        assertTrue(levelCheckbox.isSelected());
    }
}