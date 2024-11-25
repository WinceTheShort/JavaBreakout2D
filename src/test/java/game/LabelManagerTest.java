package game;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static org.example.ActionEventIDs.*;
import static org.junit.jupiter.api.Assertions.*;

class LabelManagerTest {

    @Test
    void actionPerformed() {
        JLabel levelLabel = new JLabel();
        JLabel scoreLabel = new JLabel();
        JLabel livesLabel = new JLabel();

        LabelManager labelManager = new LabelManager(levelLabel, scoreLabel, livesLabel);

        labelManager.actionPerformed(new ActionEvent(levelLabel, LEVEL_CHANGED, "TestLevel"));
        assertEquals(" TestLevel", levelLabel.getText());

        labelManager.actionPerformed(new ActionEvent(scoreLabel, SCORE_CHANGED, "3000"));
        assertEquals("3000", scoreLabel.getText());

        labelManager.actionPerformed(new ActionEvent(livesLabel, LIVES_CHANGED, "2"));
        assertEquals("Lives: 2", livesLabel.getText());

    }
}