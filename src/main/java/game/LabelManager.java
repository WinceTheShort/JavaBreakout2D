
package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.ActionEventIDs.*;

/**
 * LabelManager class used to manage JLabels for level, score, and lives in a game.
 * Implements ActionListener to update labels based on ActionEvent IDs.
 */
public class LabelManager implements ActionListener {

    /**
     * JLabel to display the current level.
     */
    JLabel levelLabel;

    /**
     * JLabel to display the current score.
     */
    JLabel scoreLabel;

    /**
     * JLabel to display the remaining lives.
     */
    JLabel livesLabel;

    /**
     * Integer to store the current score.
     */
    Integer score = 0;

    /**
     * Constructor to initialize LabelManager with level, score, and lives JLabels.
     *
     * @param levelLabel JLabel for the level.
     * @param scoreLabel JLabel for the score.
     * @param livesLabel JLabel for the lives.
     */
    public LabelManager(JLabel levelLabel, JLabel scoreLabel, JLabel livesLabel) {
        this.levelLabel = levelLabel;
        this.scoreLabel = scoreLabel;
        this.livesLabel = livesLabel;
    }

    /**
     * Handles action events to update the labels based on the event ID.
     *
     * @param e ActionEvent containing information about the action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getID()) {
            case LEVEL_CHANGED:
                levelLabel.setText(" " + e.getActionCommand());
                break;
            case LIVES_CHANGED:
                livesLabel.setText("Lives: " + e.getActionCommand());
                break;
            case SCORE_CHANGED:
                score += Integer.parseInt(e.getActionCommand());
                scoreLabel.setText(score.toString());
                break;
            default:
                break;
        }
    }
}
