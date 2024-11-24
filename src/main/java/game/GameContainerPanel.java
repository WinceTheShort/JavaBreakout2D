package game;

import level_select.LevelSlelectPanel;
import menu.AnimPanel;
import menu.MenuPanel;
import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import scores.HighscoreManager;
import util.CustomButton;
import util.LevelManager;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

public class GameContainerPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.01042);

    private static final String FONT_NAME = "minepixel";

    GamePanel gamePanel;
    CustomButton backButton;
    JLabel levelLabel;
    JLabel scoreLabel;
    JLabel livesLabel;

    transient LabelManager labelManager;

    transient LinkedList<ActionListener> actionListeners = new LinkedList<>();

    public GameContainerPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        initPanel();

        initButtons(menuPanel, animPanel);

        initComponents();
    }

    public GameContainerPanel(JFrame frame, LevelSlelectPanel levelSlelectPanel) {
        super(frame);

        initPanel();

        initButtons(levelSlelectPanel);

        initComponents();
    }

    private void initPanel(){
        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[10%, fill| 40%, fill| 40%, fill| 10%, fill]",
                "[5%, fill| 95%, fill]"
        );
        setLayout(layout);
    }

    private void initButtons(LevelSlelectPanel levelSlelectPanel) {
        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.addActionListener(this);
        backButton.addActionListener(levelSlelectPanel);
        add(backButton, "cell 0 0");
    }

    private void initButtons(MenuPanel menuPanel, AnimPanel animPanel) {
        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.addActionListener(this);
        backButton.addActionListener(menuPanel);
        backButton.addActionListener(animPanel);
        add(backButton, "cell 0 0");
    }

    private void initComponents() {

        levelLabel = new JLabel("Level Name");
        levelLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        add(levelLabel, "cell 1 0");

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        add(scoreLabel, "cell 2 0");

        livesLabel = new JLabel("Lives");
        livesLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        add(livesLabel, "cell 3 0");

        labelManager = new LabelManager(levelLabel, scoreLabel, livesLabel);

        gamePanel = new GamePanel(labelManager, this);
        add(gamePanel, "cell 0 1 4 1");

        addActionListener(gamePanel);

    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }
    private void notifyActionListeners(ActionEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiers());
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(evt);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        notifyActionListeners(e);
    }

    public void gameOver(int level){
        saveScore(level, "Game Over!");
    }
    public void youWin(int level){
        saveScore(level-1, "You Win!");
    }

    public void saveScore(int level, String title){
        HighscoreManager highscoreManager = new HighscoreManager();
        highscoreManager.loadHighscores();
        String name = JOptionPane.showInputDialog(frame, "Enter Game Name", title, JOptionPane.QUESTION_MESSAGE);
        if (name != null) {
            name = name.toLowerCase();
            name = name.replace(" ", "");
            highscoreManager.addHighscore(name, Integer.parseInt(scoreLabel.getText()), level);
            try {
                highscoreManager.saveHighscores();
            } catch (IOException e) {
                System.out.println("Error saving highscores file");
            }
        }
        backButton.doClick();
    }

    public void loadSingleLevel(LevelManager.Level level){
        gamePanel.loadSingleLevel(level);
    }

    public void leaveGame(){
        backButton.doClick();
    }

}
