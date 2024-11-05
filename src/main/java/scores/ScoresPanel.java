package scores;

import net.miginfocom.swing.MigLayout;
import menu.AnimPanel;
import org.example.GParams;
import menu.MenuPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;

public class ScoresPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    //Components
    CustomButton backButton;

    public ScoresPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        MigLayout migLayout = new MigLayout(
                "gap 0, ins 0",
                "[10%, fill| 90%, fill]",
                "[5%, fill| 95%, fill]"
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
    }
}
