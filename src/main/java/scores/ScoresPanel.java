package scores;

import net.miginfocom.swing.MigLayout;
import menu.AnimPanel;
import org.example.GParams;
import menu.MenuPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ScoresPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    //Components
    CustomButton backButton;
    HighscoreManager manager;
    ArrayList<ScoreLabel> labels = new ArrayList<>();

    public ScoresPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        MigLayout migLayout = new MigLayout(
                "gap 0, ins 0",
                "[10%, fill| 90%, fill]",
                "[5%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill| 8.6%, fill]"
        );
        setLayout(migLayout);

        initComponents(menuPanel, animPanel);
    }

    private void initComponents(MenuPanel menuPanel, AnimPanel animPanel) {
        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.addActionListener(this);
        backButton.addActionListener(menuPanel);
        backButton.addActionListener(animPanel);
        add(backButton, "cell 0 0");
        
        ScoreLabel headerLabel = new ScoreLabel();
        add(headerLabel, "cell 0 1 2 1");

        manager = new HighscoreManager();
        manager.loadHighscores();
        ArrayList<HighscoreManager.Highscore> highscores = (ArrayList<HighscoreManager.Highscore>) manager.getHighscores();
        for (int i = 0; i < highscores.size() && i < 10; i++) {
            ScoreLabel label = new ScoreLabel(i + 1);
            add(label, "cell 0 " + (i+2) + " 2 1");
            labels.add(label);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        manager.loadHighscores();
        ArrayList<HighscoreManager.Highscore> highscores = (ArrayList<HighscoreManager.Highscore>) manager.getHighscores();
        for (int i = 0; i < highscores.size() && i < 10; i++) {
            labels.get(i).setLabels(highscores.get(i));
        }
        super.actionPerformed(e);
    }
}
