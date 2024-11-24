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

/**
 * The `EditorPanel` class extends `StatePanel` and provides a user interface
 * for editing and managing a brick field. It includes buttons for actions
 * such as "New", "Load", "Save", "Save As", and "Back", and integrates components
 * like `EditorBrickFieldPanel` and `InfoBrick`.
 */
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

    /**
     * Constructs an EditorPanel with the specified JFrame, MenuPanel, and AnimPanel.
     * Sets the preferred size and layout, then initializes the components.
     *
     * @param frame      the main JFrame of the application
     * @param menuPanel  the MenuPanel to be part of this editor panel
     * @param animPanel  the AnimPanel to be included in this editor panel
     */
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

    /**
     * Initializes and configures the components within the EditorPanel. It sets up the editor brick field,
     * a set of interactive buttons (Back, New, Load, Save, Save As), and an information brick. The buttons
     * are associated with specific actions such as starting a new board, loading a saved board, and saving
     * the current state of the board. Additionally, it handles action listeners based on the presence of
     * the provided menuPanel and animPanel.
     *
     * @param menuPanel the MenuPanel instance to integrate with this editor panel, can be null
     * @param animPanel the AnimPanel instance to integrate with this editor panel, can be null
     */
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
            backButton.addActionListener(_ -> System.exit(0));
            editorBrickFieldPanel.manualStart();
        }
            add(backButton, "cell 0 0, gapleft 1mm");

        newButton = new CustomButton();
        newButton.setLabel("New");
        newButton.setFontSize(FONT_SIZE);
        newButton.addActionListener(_ -> editorBrickFieldPanel.getBricks().newBoard());
        add(newButton, "cell 1 0");

        loadButton = new CustomButton();
        loadButton.setLabel("Load");
        loadButton.setFontSize(FONT_SIZE);
        loadButton.addActionListener(_ -> editorBrickFieldPanel.getBricks().openBoard());
        add(loadButton, "cell 2 0");

        saveButton = new CustomButton();
        saveButton.setLabel("Save");
        saveButton.setFontSize(FONT_SIZE);
        saveButton.addActionListener(_ -> editorBrickFieldPanel.getBricks().saveBoard());
        add(saveButton, "cell 3 0");

        saveAsButton = new CustomButton();
        saveAsButton.setLabel("Save As");
        saveAsButton.setFontSize(FONT_SIZE);
        saveAsButton.addActionListener(_ -> editorBrickFieldPanel.getBricks().saveAs());
        add(saveAsButton, "cell 4 0");

        infoBrick = new InfoBrick(editorBrickFieldPanel);
        add(infoBrick, "cell 5 0, gapright 1mm");
        editorBrickFieldPanel.setInfoBrick(infoBrick);

    }

    /**
     * Adds an ActionListener to the list of listeners to be notified of action events.
     *
     * @param actionListener the ActionListener to be added
     */
    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    /**
     * Notifies all registered ActionListeners about an action event.
     *
     * @param e the ActionEvent that triggered the notification
     */
    private void notifyActionListeners(ActionEvent e) {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiers());
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(evt);
        }
    }

    /**
     * Handles the action event triggered by user interaction within the EditorPanel.
     * Overrides the default actionPerformed method to include additional functionality
     * like notifying registered action listeners after executing the superclass action.
     *
     * @param e the ActionEvent that triggers this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        notifyActionListeners(e);
    }
}
