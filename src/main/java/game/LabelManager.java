package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.ActionEventIDs.*;

public class LabelManager implements ActionListener {

    JLabel levelLabel;
    JLabel scoreLabel;
    JLabel livesLabel;

    Integer score = 0   ;

    public LabelManager(JLabel levelLabel, JLabel scoreLabel, JLabel livesLabel) {
        this.levelLabel = levelLabel;
        this.scoreLabel = scoreLabel;
        this.livesLabel = livesLabel;
    }

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
