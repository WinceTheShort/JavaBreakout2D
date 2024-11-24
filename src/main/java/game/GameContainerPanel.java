
package game;


import level_select.LevelSelectPanel;
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

/**
 * The GameContainerPanel class is a custom JPanel that serves as the primary container for the game's various components.
 * It manages the layout, buttons, labels, and game panel, and integrates the highscore management system.
 */
public class GameContainerPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    /**
     * Calculates the font size based on the screen width to ensure consistency across multiple resolutions.
     */
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.01042);

    /**
     * The name of the font used in the panel.
     */
    private static final String FONT_NAME = "minepixel";

    /**
     * Panel that displays the game contents.
     */
    GamePanel gamePanel;

    /**
     * Button to go back to the previous menu or screen.
     */
    CustomButton backButton;

    /**
     * Label to display the current level name.
     */
    JLabel levelLabel;

    /**
     * Label to display the current score.
     */
    JLabel scoreLabel;

    /**
     * Label to display the remaining lives.
     */
    JLabel livesLabel;

    /**
     * Manager to handle updates to the labels on the panel.
     */
    transient LabelManager labelManager;

    /**
     * List of action listeners for various button actions.
     */
    transient LinkedList<ActionListener> actionListeners = new LinkedList<>();

    /**
     * Constructs a GameContainerPanel with the provided frame, menu panel, and animation panel.
     *
     * @param frame      The main application frame.
     * @param menuPanel  The panel displaying the menu.
     * @param animPanel  The panel displaying animations.
     */
    public GameContainerPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        initPanel();

        initButtons(menuPanel, animPanel);

        initComponents();
    }

    /**
     * Constructs a GameContainerPanel with the provided frame and level selection panel.
     *
     * @param frame             The main application frame.
     * @param levelSelectPanel  The panel for selecting levels.
     */
    public GameContainerPanel(JFrame frame, LevelSelectPanel levelSelectPanel) {
        super(frame);

        initPanel();

        initButtons(levelSelectPanel);

        initComponents();
    }

    /**
     * Initializes the panel's layout and size.
     */
    private void initPanel(){
        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[10%, fill| 40%, fill| 40%, fill| 10%, fill]",
                "[5%, fill| 95%, fill]"
        );
        setLayout(layout);
    }

    /**
     * Initializes the back button and adds action listeners for level selection.
     *
     * @param levelSelectPanel  The panel for selecting levels.
     */
    private void initButtons(LevelSelectPanel levelSelectPanel) {
        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.addActionListener(this);
        backButton.addActionListener(levelSelectPanel);
        add(backButton, "cell 0 0");
    }

    /**
     * Initializes the back button and adds action listeners for the menu and animation panels.
     *
     * @param menuPanel  The panel displaying the menu.
     * @param animPanel  The panel displaying animations.
     */
    private void initButtons(MenuPanel menuPanel, AnimPanel animPanel) {
        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.addActionListener(this);
        backButton.addActionListener(menuPanel);
        backButton.addActionListener(animPanel);
        add(backButton, "cell 0 0");
    }

    /**
     * Initializes the labels, game panel, and adds action listeners.
     */
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

    /**
     * Adds an action listener to the panel.
     *
     * @param actionListener  The action listener to be added.
     */
    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }
    /**
     * Notifies all registered action listeners of an action event.
     *
     * @param e  The action event to notify listeners of.
     */
    private void notifyActionListeners(ActionEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiers());
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(evt);
        }
    }

    /**
     * Handles action events and notifies all registered listeners.
     *
     * @param e  The action event to respond to.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        notifyActionListeners(e);
    }

    /**
     * Handles the game over event.
     *
     * @param level  The level at which the game ended.
     */
    public void gameOver(int level){
        saveScore(level, "Game Over!");
    }

    /**
     * Handles the win event.
     *
     * @param level  The level at which the player won.
     */
    public void youWin(int level){
        saveScore(level-1, "You Win!");
    }

    /**
     * Saves the score to the highscore manager.
     *
     * @param level  The level at which the game ended or was won.
     * @param title  The title for the input dialog when saving the score.
     */
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

    /**
     * Loads a single level in the game panel.
     *
     * @param level  The level to be loaded.
     */
    public void loadSingleLevel(LevelManager.Level level){
        gamePanel.loadSingleLevel(level);
    }

    /**
     * Simulates clicking the back button to leave the game.
     */
    public void leaveGame(){
        backButton.doClick();
    }

}
