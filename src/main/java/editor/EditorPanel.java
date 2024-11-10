package editor;

import net.miginfocom.swing.MigLayout;
import menu.AnimPanel;
import org.example.GParams;
import menu.MenuPanel;
import util.CustomButton;
import util.StatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class EditorPanel extends StatePanel {
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    EditorBrickFieldPanel editorBrickFieldPanel;
    CustomButton saveButton;
    CustomButton saveAsButton;
    CustomButton loadButton;
    CustomButton newButton;
    CustomButton backButton;
    InfoBrick infoBrick;

    transient LinkedList<ActionListener> actionListeners = new LinkedList<>();

    public EditorPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        setPreferredSize(new Dimension(GParams.SCREEN_WIDTH, GParams.SCREEN_HEIGHT));
        MigLayout migLayout = new MigLayout(
                "gap 1mm, ins 0",
                "[10%, fill| 20%, fill| 20%, fill| 20%, fill| 20%, fill| 10%, fill]",
                "1mm [5%, fill| 95%, fill]"
        );
        setLayout(migLayout);

        initComponents(menuPanel, animPanel);
    }

    private void initComponents(MenuPanel menuPanel, AnimPanel animPanel) {
        editorBrickFieldPanel = new EditorBrickFieldPanel();
        add(editorBrickFieldPanel, "cell 0 1 6 1");
        addActionListener(editorBrickFieldPanel);

        backButton = new CustomButton();
        backButton.setLabel("Back");
        backButton.setFontSize(FONT_SIZE);
        if (menuPanel != null && animPanel != null) {
            backButton.addActionListener(this);
            backButton.addActionListener(menuPanel);
            backButton.addActionListener(animPanel);
        } else {
            backButton.addActionListener(e -> System.exit(0));
            editorBrickFieldPanel.manualStart();
        }
            add(backButton, "cell 0 0, gapleft 1mm");

        newButton = new CustomButton();
        newButton.setLabel("New");
        newButton.setFontSize(FONT_SIZE);
        add(newButton, "cell 1 0");

        loadButton = new CustomButton();
        loadButton.setLabel("Load");
        loadButton.setFontSize(FONT_SIZE);
        add(loadButton, "cell 2 0");

        saveButton = new CustomButton();
        saveButton.setLabel("Save");
        saveButton.setFontSize(FONT_SIZE);
        add(saveButton, "cell 3 0");

        saveAsButton = new CustomButton();
        saveAsButton.setLabel("Save As");
        saveAsButton.setFontSize(FONT_SIZE);
        add(saveAsButton, "cell 4 0");

        infoBrick = new InfoBrick(editorBrickFieldPanel);
        add(infoBrick, "cell 5 0, gapright 1mm");
        editorBrickFieldPanel.setInfoBrick(infoBrick);

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
}
