package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Switchable state panel
/**
 * The `StatePanel` class represents a JPanel that can switch its state
 * and be added or removed from a JFrame based on an action event.
 */
public class StatePanel extends JPanel implements ActionListener {
    /**
     * The frame to which this panel belongs.
     */
    protected JFrame frame;

    /**
     * Indicates whether the panel is currently in the state.
     */
    protected boolean inState = false;

    /**
     * Constructs a StatePanel.
     *
     * @param frame the JFrame to which this panel belongs.
     */
    public StatePanel(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Toggles the state of the panel and updates the JFrame accordingly.
     *
     * @param e the action event triggering this method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Toggle the state
        inState = !inState;

        if (inState){
            // Add panel to the frame
            frame.add(this, BorderLayout.CENTER);
            repaint();
            frame.pack();
        } else {
            // Remove panel from the frame
            frame.remove(this);
        }
        // Repaint the frame
        frame.repaint();
    }
}
