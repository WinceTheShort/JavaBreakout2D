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

public class LevelSlelectPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    //Components
    CustomButton backButton;
    CustomButton playButton;
    PreviewPane previewPane;
    LevelScrollPane levelScrollPane;

    GameContainerPanel gameContainerPanel;

    public LevelSlelectPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
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

    @Override
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        levelScrollPane.loadLevels();
    }

}
