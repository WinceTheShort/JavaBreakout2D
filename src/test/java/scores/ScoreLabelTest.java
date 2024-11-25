package scores;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ScoreLabelTest {

    @Test
    void setLabels() {
        ScoreLabel scoreLabel = new ScoreLabel(1);
        scoreLabel.setLabels(new HighscoreManager.Highscore("Joe", 2000, 4));

        assertEquals(1, scoreLabel.getPos());
        assertEquals("Joe", scoreLabel.getPlayerName());
        assertEquals(2000, scoreLabel.getScore());
        assertEquals(4, scoreLabel.getReachedLevel());
    }

    @Test
    void createHeader() {
        ScoreLabel scoreLabel = new ScoreLabel();
        LinkedList<JLabel> labels = (LinkedList<JLabel>) scoreLabel.getLabels();

        assertEquals("#", labels.getFirst().getText());
        assertEquals(" name", labels.get(1).getText());
        assertEquals(" lvl", labels.get(2).getText());
        assertEquals(" score", labels.getLast().getText());
    }
}