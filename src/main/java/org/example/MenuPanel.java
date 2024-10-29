package org.example;

import editor.BrickFieldPanel;
import level_select.LevelSlelectPanel;
import net.miginfocom.swing.MigLayout;
import scores.ScoresPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends StatePanel{
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.03125);

    private transient JLabel titleLabel = new JLabel();
    private transient CustomButton playButton = new CustomButton();
    private transient CustomButton scoresButton = new CustomButton();
    private transient CustomButton levelsButton = new CustomButton();
    private transient CustomButton editorButton = new CustomButton();
    private transient CustomButton exitButton = new CustomButton();
    private transient AnimPanel animPanel = new AnimPanel();

    private transient GamePanel gamePanel;
    private transient ScoresPanel scoresPanel;
    private transient LevelSlelectPanel levelSlelectPanel;
    private transient BrickFieldPanel brickFieldPanel;

    public MenuPanel(JFrame frame){
        super(frame);
        inState = true;

        gamePanel = new GamePanel(frame, this, animPanel);
        scoresPanel = new ScoresPanel(frame, this, animPanel);
        levelSlelectPanel = new LevelSlelectPanel(frame, this, animPanel);
        brickFieldPanel = new BrickFieldPanel(frame, this, animPanel);

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH,GParams.SCREEN_HEIGHT));
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[20%, align 50%, fill][80%, align 50%, fill]",
                "[30%, fill|14%, fill|14%, fill|14%, fill|14%, fill|14%, fill]"
        );
        setLayout(layout);

        setBackground(Color.GRAY);
        setFocusable(true);

        add(animPanel, "cell 1 0 1 6");

        titleLabel.setFont(new Font("minepixel",Font.PLAIN,FONT_SIZE));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setText("<html><body>Brickem<br>Breakem</body></html>");
        titleLabel.setBackground(Color.WHITE);
        add(titleLabel, "cell 0 0");

        playButton.setLabel("Play");
        playButton.setFontSize(FONT_SIZE);
        playButton.addActionListener(animPanel);
        playButton.addActionListener(this);
        playButton.addActionListener(gamePanel);
        add(playButton, "cell 0 1");

        scoresButton.setLabel("Scores");
        scoresButton.setFontSize(FONT_SIZE);
        scoresButton.addActionListener(animPanel);
        scoresButton.addActionListener(this);
        scoresButton.addActionListener(scoresPanel);
        add(scoresButton, "cell 0 2");

        levelsButton.setLabel("Levels");
        levelsButton.setFontSize(FONT_SIZE);
        levelsButton.addActionListener(animPanel);
        levelsButton.addActionListener(this);
        levelsButton.addActionListener(levelSlelectPanel);
        add(levelsButton, "cell 0 3");

        editorButton.setLabel("Editor");
        editorButton.setFontSize(FONT_SIZE);
        editorButton.addActionListener(animPanel);
        editorButton.addActionListener(this);
        editorButton.addActionListener(brickFieldPanel);
        add(editorButton, "cell 0 4");

        exitButton.setLabel("Exit");
        exitButton.setFontSize(FONT_SIZE);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, "cell 0 5");

        animPanel.start();
    }
}
