package menu;

import editor.EditorPanel;
import game.GameContainerPanel;
import level_select.LevelSelectPanel;
import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import scores.ScoresPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * The MenuPanel class constructs a menu interface for the application. It extends StatePanel
 * and initializes various panels for gameplay, scores, level selection, and editing, as well
 * as setting up the layout and event listeners for buttons.
 */
public class MenuPanel extends StatePanel{
    /**
     * The constant FONT_SIZE calculates the font size for the menu based on the screen width to maintain
     * consistent font sizing across different screen resolutions.
     */
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.03125);

    /**
     * Constructs the MenuPanel with the specified JFrame.
     *
     * @param frame the main frame of the application
     */
    public MenuPanel(JFrame frame){
        super(frame);
        inState = true;

        AnimPanel animPanel = new AnimPanel(); // Panel to display animations
        GameContainerPanel gameContainerPanel = new GameContainerPanel(frame, this, animPanel); // Panel for the game container
        ScoresPanel scoresPanel = new ScoresPanel(frame, this, animPanel); // Panel to show scores
        LevelSelectPanel levelSelectPanel = new LevelSelectPanel(frame, this, animPanel); // Panel for level selection
        EditorPanel editorPanel = new EditorPanel(frame, this, animPanel); // Panel for level editor

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH,GParams.SCREEN_HEIGHT));
        MigLayout layout = new MigLayout(
                "gap 0.2%, ins 0.2%",
                "[20%, fill|80%, fill]",
                "[30%, fill|14%, fill|14%, fill|14%, fill|14%, fill|14%, fill]"
        );
        setLayout(layout);

        setBackground(Color.GRAY);
        setFocusable(true);

        add(animPanel, "cell 1 0 1 6");

        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("minepixel",Font.PLAIN,FONT_SIZE));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setText("<html><body>Brickem<br>Breakem</body></html>");
        titleLabel.setBackground(Color.WHITE);
        add(titleLabel, "cell 0 0");

        CustomButton playButton = new CustomButton();
        playButton.setLabel("Play");
        playButton.setFontSize(FONT_SIZE);
        playButton.addActionListener(animPanel);
        playButton.addActionListener(this);
        playButton.addActionListener(gameContainerPanel);
        add(playButton, "cell 0 1");

        CustomButton scoresButton = new CustomButton();
        scoresButton.setLabel("Scores");
        scoresButton.setFontSize(FONT_SIZE);
        scoresButton.addActionListener(animPanel);
        scoresButton.addActionListener(this);
        scoresButton.addActionListener(scoresPanel);
        add(scoresButton, "cell 0 2");

        CustomButton levelsButton = new CustomButton();
        levelsButton.setLabel("Levels");
        levelsButton.setFontSize(FONT_SIZE);
        levelsButton.addActionListener(animPanel);
        levelsButton.addActionListener(this);
        levelsButton.addActionListener(levelSelectPanel);
        add(levelsButton, "cell 0 3");

        CustomButton editorButton = new CustomButton();
        editorButton.setLabel("Editor");
        editorButton.setFontSize(FONT_SIZE);
        editorButton.addActionListener(animPanel);
        editorButton.addActionListener(this);
        editorButton.addActionListener(editorPanel);
        add(editorButton, "cell 0 4");

        CustomButton exitButton = new CustomButton();
        exitButton.setLabel("Exit");
        exitButton.setFontSize(FONT_SIZE);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, "cell 0 5");

        animPanel.start();
    }
}
