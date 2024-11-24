package level_select;

import game.GameContainerPanel;
import net.miginfocom.swing.MigLayout;
import menu.AnimPanel;
import org.example.GParams;
import menu.MenuPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The {@code LevelSelectPanel} class represents the panel used for selecting levels in the game.
 * It extends {@link StatePanel} and manages the layout and components for level selection, including
 * navigation buttons and a preview pane.
 */
public class LevelSelectPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    //Components
    CustomButton backButton;
    CustomButton playButton;
    PreviewPane previewPane;
    LevelScrollPane levelScrollPane;

    GameContainerPanel gameContainerPanel;

    /**
     * Constructs a new {@code LevelSelectPanel} with the specified frame, menu panel, and animation panel.
     *
     * @param frame the main application frame
     * @param menuPanel the menu panel
     * @param animPanel the animation panel
     */
    public LevelSelectPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);
        gameContainerPanel = new GameContainerPanel(frame, this);

        MigLayout migLayout = new MigLayout(
                "gap 0, ins 0",
                "[10%, align 50%, fill|20%, align 50%, fill|60%, align 50%, fill|10%, align 50%, fill]",
                "[5%, align 50%, fill|95%, align 50%, fill]"
        );
        setLayout(migLayout);

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        setFocusable(true);

        initComponents(menuPanel, animPanel);
    }

    /**
     * Initializes the components for this panel, including back and play buttons, and attaches event listeners.
     *
     * @param menuPanel the menu panel
     * @param animPanel the animation panel
     */
    private void initComponents(MenuPanel menuPanel, AnimPanel animPanel) {
        backButton = new CustomButton();
        backButton.addActionListener(this);
        backButton.addActionListener(menuPanel);
        backButton.addActionListener(animPanel);
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        backButton.setOpaque(true);
        add(backButton, "cell 0 0");

        playButton = new CustomButton();
        playButton.addActionListener(this);
        playButton.addActionListener(_ -> gameContainerPanel.loadSingleLevel(levelScrollPane.getSelectedLevel()));
        playButton.addActionListener(gameContainerPanel);
        playButton.setLabel("Play");
        playButton.setFontSize(FONT_SIZE);
        add(playButton, "cell 4 0");

        previewPane = new PreviewPane();
        previewPane.setBackground(Color.WHITE);
        add(previewPane, "cell 2 1 3 1");

        levelScrollPane = new LevelScrollPane();
        add(levelScrollPane, "cell 0 1 2 1");
        levelScrollPane.addActionListener(_ -> previewPane.loadLevel(levelScrollPane.getSelectedLevel()));
    }

    /**
     * Handles action events, including loading levels into the scroll pane.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        levelScrollPane.loadLevels();
    }

}
