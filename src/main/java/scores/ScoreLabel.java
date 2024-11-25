package scores;

import net.miginfocom.swing.MigLayout;
import org.example.GParams;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code ScoreLabel} class represents a customized JPanel with labels arranged
 * using MigLayout to display scores, player names, and levels consistently across
 * different screen resolutions.
 */
public class ScoreLabel extends JPanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);
    private static final String FONT_NAME = "minepixel";

    LinkedList<JLabel> labels = new LinkedList<>();

    /**
     * Constructs a {@code ScoreLabel} object with a specific position.
     *
     * @param pos the position to be displayed in the label
     */
    public ScoreLabel(int pos) {
        setBackground(Color.gray);
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[5%, fill|20%, fill|5%, fill|70%, fill]",
                "[100%, fill]"
        );
        setLayout(layout);

        JLabel posLabel = new JLabel(String.valueOf(pos));
        posLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(posLabel);
        labels.add(posLabel);

        JLabel nameLabel = new JLabel();
        add(nameLabel);
        labels.add(nameLabel);

        JLabel levelLabel = new JLabel();
        add(levelLabel);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        labels.add(levelLabel);

        JLabel scoreLabel = new JLabel();
        add(scoreLabel);
        labels.add(scoreLabel);

        for (JLabel label : labels) {
            label.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            label.setBorder(BorderFactory.createMatteBorder(4,2,2,2,label.getForeground()));
            label.setVerticalAlignment(SwingConstants.CENTER);
        }

    }
    /**
     * Constructs a {@code ScoreLabel} object with default labels for position, name, level, and score.
     */
    public ScoreLabel() {
        setBackground(Color.gray.darker());
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[5%, fill|20%, fill|5%, fill|70%, fill]",
                "[100%, fill]"
        );
        setLayout(layout);

        JLabel posLabel = new JLabel("#");
        posLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(posLabel);
        labels.add(posLabel);

        JLabel nameLabel = new JLabel(" name");
        add(nameLabel);
        labels.add(nameLabel);

        JLabel levelLabel = new JLabel(" lvl");
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(levelLabel);
        labels.add(levelLabel);

        JLabel scoreLabel = new JLabel(" score");
        add(scoreLabel);
        labels.add(scoreLabel);

        for (JLabel label : labels) {
            label.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            label.setBorder(BorderFactory.createMatteBorder(4,2,2,2,label.getForeground()));
            label.setVerticalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * Sets the labels for the score panel using the provided highscore data.
     *
     * @param highscore the highscore data containing player name, level reached, and score
     */
    public void setLabels(HighscoreManager.Highscore highscore) {
        labels.get(1).setText(" " + highscore.getPlayerName());
        labels.get(2).setText(" " + highscore.getLvlReached());
        labels.get(3).setText(" " + highscore.getScore());
    }

    public int getPos() {
        return Integer.parseInt(labels.getFirst().getText());
    }

    public String getPlayerName() {
        return labels.get(1).getText().trim();
    }

    public int getReachedLevel() {
        return Integer.parseInt(labels.get(2).getText().trim());
    }

    public int getScore() {
        return Integer.parseInt(labels.get(3).getText().trim());
    }

    public List<JLabel> getLabels() {
        return labels;
    }

}
