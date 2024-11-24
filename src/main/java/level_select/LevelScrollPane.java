
package level_select;

import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import util.LevelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * LevelScrollPane is a custom JScrollPane that displays game levels as checkboxes
 * and handles the selection of these levels.
 */
public class LevelScrollPane extends JScrollPane implements ActionListener {

    /**
     * The preferred height of the level checkboxes based on the screen height.
     */
    double preferedHeight = GParams.SCREEN_HEIGHT*0.05;

    /**
     * A list of action listeners to be notified when a level is selected.
     */
    private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

    /**
     * The panel holding the level checkboxes.
     */
    JPanel panel;

    /**
     * The currently selected level.
     */
    transient LevelManager.Level selectedLevel;

    /**
     * The level manager that loads and provides access to the levels.
     */
    transient LevelManager levelManager;

    /**
     * Constructs a new LevelScrollPane and initializes the level manager.
     */
    public LevelScrollPane() {
        super();

        levelManager = new LevelManager();
    }
    /**
     * Loads the levels using the level manager and displays them as checkboxes.
     */
    public void loadLevels(){
        levelManager.loadLevels();
        loadLabels();
    }
    /**
     * Loads the checkboxes for official and custom levels into the panel.
     */
    private void loadLabels(){
        panel = new JPanel();
        panel.setLayout(new MigLayout("gap 0, ins 0", "[100%, fill]"));
        panel.setBackground(Color.BLUE);
        setViewportView(panel);

        // Create and add the checkbox for official levels
        LevelCheckbox official = new LevelCheckbox("official");
        panel.add(official, "cell 0 0, h " + preferedHeight);
        int i = 1;
        // Add checkboxes for each official level
        for (LevelManager.Level level : levelManager.getOfficialLevels()){
            LevelCheckbox levelCheckbox = new LevelCheckbox(level, i);
            levelCheckbox.addActionListener(this);
            addActionListener(levelCheckbox);
            panel.add(levelCheckbox, "cell 0 " + i + ", h " + preferedHeight);
            i++;
        }
        // Create and add the checkbox for custom levels
        LevelCheckbox custom = new LevelCheckbox("custom");
        panel.add(custom, "cell 0 " + i + ", h " + preferedHeight);
        i++;
        int j = 1;
        // Add checkboxes for each custom level
        for (LevelManager.Level level : levelManager.getCustomLevels()){
            LevelCheckbox levelCheckbox = new LevelCheckbox(level, j);
            levelCheckbox.addActionListener(this);
            addActionListener(levelCheckbox);
            panel.add(levelCheckbox, "cell 0 " + i + ", h " + preferedHeight);
            i++;
            j++;
        }
    }
    /**
     * Adds an action listener to be notified when a level is selected.
     *
     * @param listener the action listener to add
     */
    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    /**
     * Notifies all registered action listeners that a level has been selected.
     */
    private void notifyListeners(){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, selectedLevel.getLevelName());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }

    /**
     * Handles the action event when a level checkbox is selected and notifies
     * all registered listeners.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedLevel = levelManager.getLevelByName(e.getActionCommand());
        notifyListeners();
    }
    /**
     * Returns the currently selected level.
     *
     * @return the selected level
     */
    public LevelManager.Level getSelectedLevel() {
        return selectedLevel;
    }
}
