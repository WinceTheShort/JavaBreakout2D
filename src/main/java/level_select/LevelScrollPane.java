package level_select;

import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import util.LevelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LevelScrollPane extends JScrollPane implements ActionListener {
    double preferedHeight = GParams.SCREEN_HEIGHT*0.05;

    private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

    JPanel panel;
    LevelManager.Level selectedLevel;
    LevelManager levelManager;

    public LevelScrollPane() {
        super();

        levelManager = new LevelManager();
    }
    public void loadLevels(){
        levelManager.loadLevels();
        loadLabels();
    }
    private void loadLabels(){
        panel = new JPanel();
        panel.setLayout(new MigLayout("gap 0, ins 0", "[100%, fill]"));
        panel.setBackground(Color.BLUE);
        setViewportView(panel);

        LevelCheckbox official = new LevelCheckbox("official");
        panel.add(official, "cell 0 0, h " + preferedHeight);
        int i = 1;
        for (LevelManager.Level level : levelManager.getOfficialLevels()){
            LevelCheckbox levelCheckbox = new LevelCheckbox(level, i);
            levelCheckbox.addActionListener(this);
            addActionListener(levelCheckbox);
            panel.add(levelCheckbox, "cell 0 " + i + ", h " + preferedHeight);
            i++;
        }
        LevelCheckbox custom = new LevelCheckbox("custom");
        panel.add(custom, "cell 0 " + i + ", h " + preferedHeight);
        i++;
        int j = 1;
        for (LevelManager.Level level : levelManager.getCustomLevels()){
            LevelCheckbox levelCheckbox = new LevelCheckbox(level, j);
            levelCheckbox.addActionListener(this);
            addActionListener(levelCheckbox);
            panel.add(levelCheckbox, "cell 0 " + i + ", h " + preferedHeight);
            i++;
            j++;
        }
    }
    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    private void notifyListeners(){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, selectedLevel.getLevelName());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedLevel = levelManager.getLevelByName(e.getActionCommand());
        notifyListeners();
    }
    public LevelManager.Level getSelectedLevel() {
        return selectedLevel;
    }
}
